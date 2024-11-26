import os
import requests
from flask import Flask, request, jsonify
import numpy as np
from PIL import Image
import io
import firebase_admin
from firebase_admin import credentials, firestore, auth
from google.cloud import storage
import tensorflow as tf
from datetime import datetime

# Inisialisasi Flask
app = Flask(__name__)

# Inisialisasi Firebase
cred = credentials.Certificate('firebase.json')  # Pastikan file firebase.json ada di direktori yang sama
firebase_admin.initialize_app(cred)
db = firestore.client()

# Konfigurasi Google Cloud Storage Bucket
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "bucket.json"  # Pastikan file bucket.json ada di direktori yang sama
BUCKET_NAME = "rasanusa"  # Ganti dengan nama bucket kamu

# Konfigurasi model dan path
IMAGE_SIZE = (224, 224)  # Ukuran gambar untuk model
MODEL_URL = "https://storage.googleapis.com/rasanusa/models/classification_model.h5"  # URL model di Google Cloud Storage
H5_MODEL_PATH = "classification_model.h5"  # Path untuk menyimpan model .h5
TFLITE_MODEL_PATH = "classification_model.tflite"  # Path untuk menyimpan model .tflite

# Simpan hasil prediksi global
predicted_result = {}

# Fungsi untuk mengunduh model dari URL
def download_model(url, local_path):
    if not os.path.exists(local_path):
        try:
            print(f"Downloading model from {url}...")
            response = requests.get(url, stream=True)
            response.raise_for_status()
            with open(local_path, "wb") as f:
                for chunk in response.iter_content(chunk_size=8192):
                    if chunk:
                        f.write(chunk)
            print(f"Model downloaded and saved to {local_path}.")
        except Exception as e:
            print(f"Error downloading model: {e}")
            raise

# Fungsi untuk mengonversi model Keras ke TFLite
def convert_to_tflite(h5_model_path, tflite_model_path):
    if not os.path.exists(tflite_model_path):
        print(f"Converting {h5_model_path} to {tflite_model_path}...")
        model = tf.keras.models.load_model(h5_model_path)
        converter = tf.lite.TFLiteConverter.from_keras_model(model)
        tflite_model = converter.convert()
        with open(tflite_model_path, "wb") as f:
            f.write(tflite_model)
        print(f"Model converted and saved to {tflite_model_path}.")
    else:
        print(f"TFLite model already exists at {tflite_model_path}.")

# Fungsi untuk memproses gambar yang diunggah
def load_and_preprocess_image(uploaded_image):
    img = Image.open(io.BytesIO(uploaded_image.read()))
    img = img.convert('RGB')
    img = img.resize(IMAGE_SIZE)
    img = np.array(img)
    img = img.astype('float32') / 255.0
    img = np.expand_dims(img, axis=0)
    return img

# Fungsi untuk prediksi gambar menggunakan model .h5
def predict_image(uploaded_image):
    img = load_and_preprocess_image(uploaded_image)

    predictions = model.predict(img)  # Gunakan model.predict untuk prediksi
    predicted_class_index = np.argmax(predictions, axis=1)

    class_labels = ['kerak_telor', 'mie_aceh', 'papeda', 'pempek', 'soto_banjar', 'tahu_sumedang', 'bika_ambon', 'rendang', 'sate_lilit', 'es_pisang_ijo', 'lumpia']
    predicted_class_name = class_labels[predicted_class_index[0]]

    return predicted_class_name

# Fungsi untuk mengambil data dari Firestore
def makanan_data(predicted_class):
    doc_ref = db.collection('makanan').document(predicted_class)
    doc = doc_ref.get()
    if doc.exists:
        return doc.to_dict()  # Pastikan kita mengonversi data menjadi dictionary yang bisa diserialisasi
    else:
        return {"error": f"No document found for class: {predicted_class}"}

# Fungsi untuk mengunggah file ke Google Cloud Storage
def upload_to_bucket(bucket_name, file_content, destination_blob_name):
    storage_client = storage.Client()  # Client menggunakan kredensial JSON
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(destination_blob_name)
    blob.upload_from_string(file_content, content_type='image/jpeg')
    return f"gs://{bucket_name}/{destination_blob_name}"

# Fungsi untuk menangani serialisasi tipe data yang tidak standar (misalnya datetime)
def default_serializer(obj):
    if isinstance(obj, datetime):
        return obj.isoformat()  # Mengubah datetime menjadi string ISO
    raise TypeError(f"Type {type(obj)} not serializable")

@app.route('/', methods=['GET'])
def index():
    response = {"message": "RasaNusa api is running..."}
    return jsonify(response), 200

@app.route('/register', methods=['POST'])
def register():
    """
    Endpoint untuk mendaftar pengguna baru dan menyimpan data pengguna ke Firestore.
    """
    try:
        # Ambil data dari request
        email = request.json.get('email')
        password = request.json.get('password')
        username = request.json.get('username')

        if not email or not password or not username:
            return jsonify({"error": "Email, password, and username are required"}), 400

        # Buat akun pengguna menggunakan Firebase Authentication
        user = auth.create_user(
            email=email,
            password=password
        )

        # Simpan data pengguna (username) ke Firestore
        user_ref = db.collection('users').document(user.uid)
        user_ref.set({
            'username': username,
            'email': email,
            'created_at': firestore.SERVER_TIMESTAMP
        })

        return jsonify({
            "message": "User registered successfully",
            "uid": user.uid,
            "username": username
        }), 201

    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/predict', methods=['POST'])
def predict():
    global predicted_result
    if 'image' not in request.files:
        return jsonify({"error": "No file part"}), 400
    uploaded_image = request.files['image']
    if uploaded_image.filename == '':
        return jsonify({"error": "No selected file"}), 400

    # Prediksi kelas
    predicted_class = predict_image(uploaded_image)

    # Simpan gambar ke bucket
    uploaded_image.seek(0)
    image_content = uploaded_image.read()
    destination_blob_name = f"predictions/{predicted_class}/{uploaded_image.filename}"
    bucket_url = upload_to_bucket(BUCKET_NAME, image_content, destination_blob_name)

    # Ambil data dari Firestore
    document_data = makanan_data(predicted_class)

    # Menyimpan hasil prediksi ke dalam Firestore Scan History
    history_ref = db.collection('scan_history').document()
    history_ref.set({
        'predicted_class': predicted_class,
        'document_data': document_data,
        'image_url': bucket_url,
        'timestamp': firestore.SERVER_TIMESTAMP  # Menyimpan waktu prediksi
    })

    predicted_result = {
        "predicted_class": predicted_class,
        "document_data": document_data,
        "image_url": bucket_url
    }

    return jsonify(predicted_result), 201

@app.route('/history', methods=['GET'])
def get_history():
    try:
        # Ambil semua dokumen dari koleksi 'scan_history'
        history_ref = db.collection('scan_history')
        docs = history_ref.stream()

        history = []
        for doc in docs:
            history.append(doc.to_dict())  # Menambahkan data dari setiap dokumen ke dalam list

        return jsonify(history), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/result/<field>', methods=['GET'])
def get_field(field):
    if field in predicted_result:
        return jsonify({field: predicted_result[field]}), 200
    elif field in predicted_result.get('document_data', {}):
        return jsonify({field: predicted_result['document_data'][field]}), 200
    else:
        return jsonify({"error": f"Field '{field}' not found"}), 404

@app.route('/makanan/<doc_id>/<field>', methods=['GET'])
def makanan(doc_id, field):
    doc_ref = db.collection('makanan').document(doc_id)
    doc = doc_ref.get()
    if not doc.exists:
        return jsonify({"error": f"Document with id '{doc_id}' not found"}), 404

    document_data = doc.to_dict()
    if field in document_data:
        return jsonify({field: document_data[field]}), 200
    else:
        return jsonify({"error": f"Field '{field}' not found in document '{doc_id}'"}), 404

@app.route('/makanan/<doc_id>', methods=['GET'])
def get_full_document(doc_id):
    doc_ref = db.collection('makanan').document(doc_id)
    doc = doc_ref.get()
    if doc.exists:
        return jsonify(doc.to_dict()), 200
    else:
        return jsonify({"error": "Document not found"}), 404

# --- Endpoint baru untuk mengunduh model ---
@app.route('/downloadModel', methods=['GET'])
def download_model_endpoint():
    try:
        download_model(MODEL_URL, H5_MODEL_PATH)
        return jsonify({"message": f"Model downloaded to {H5_MODEL_PATH}"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# --- Endpoint baru untuk mengonversi model ---
@app.route('/convertModel', methods=['GET'])
def convert_model_endpoint():
    try:
        convert_to_tflite(H5_MODEL_PATH, TFLITE_MODEL_PATH)
        return jsonify({"message": f"Model converted to {TFLITE_MODEL_PATH}"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# --- Muat model .h5 setelah server berjalan --- 
download_model(MODEL_URL, H5_MODEL_PATH)  # Unduh model saat aplikasi dimulai
model = tf.keras.models.load_model(H5_MODEL_PATH)

# Jalankan Flask App
if __name__ == '__main__':
    app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 8080)))