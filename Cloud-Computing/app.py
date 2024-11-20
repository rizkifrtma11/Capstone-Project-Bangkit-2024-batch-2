import os
from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
import numpy as np
from PIL import Image
import io
import firebase_admin
from firebase_admin import credentials, firestore
from google.cloud import storage  # Import Google Cloud Storage

# Inisialisasi Flask
app = Flask(__name__)

# Inisialisasi Firebase
cred = credentials.Certificate('firebase.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

# Konfigurasi Google Cloud Storage Bucket
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "bucket.json"
BUCKET_NAME = "rasanusa"

# Konfigurasi model dan path
IMAGE_SIZE = (224, 224)  # Ukuran gambar untuk model
classification_model = load_model('predick.keras')

# Simpan hasil prediksi global
predicted_result = {}

# Default route
@app.route('/', methods=['GET'])
def index():
    """
    Root endpoint memberikan deskripsi layanan.
    """
    response = {
        "message": "RasaNusa api is running..."
    }
    return jsonify(response), 200

# Fungsi untuk memproses gambar yang diunggah
def load_and_preprocess_image(uploaded_image):
    img = Image.open(io.BytesIO(uploaded_image.read()))
    img = img.convert('RGB')
    img = img.resize(IMAGE_SIZE)
    img = np.array(img)
    img = img.astype('float32') / 255.0
    img = np.expand_dims(img, axis=0)
    return img

# Fungsi untuk prediksi gambar
def predict_image(uploaded_image):
    img = load_and_preprocess_image(uploaded_image)
    predictions = classification_model.predict(img)
    predicted_class_index = np.argmax(predictions, axis=1)
    class_labels = ['kerak_telor', 'mie_aceh', 'papeda', 'pempek', 'soto_banjar', 'tahu_sumedang', 'bika_ambon', 'rendang', 'sate_lilit', 'es_pisang_ijo', 'lumpia']
    predicted_class_name = class_labels[predicted_class_index[0]]
    return predicted_class_name

# Fungsi untuk mengambil data dari Firestore
def get_document_data(predicted_class):
    doc_ref = db.collection('makanan').document(predicted_class)
    doc = doc_ref.get()
    if doc.exists:
        return doc.to_dict()
    else:
        return {"error": f"No document found for class: {predicted_class}"}

# Fungsi untuk mengunggah file ke Google Cloud Storage
def upload_to_bucket(bucket_name, file_content, destination_blob_name):
    """
    Mengunggah file ke Google Cloud Storage bucket.
    """
    storage_client = storage.Client()  # Client menggunakan kredensial JSON
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(destination_blob_name)
    blob.upload_from_string(file_content, content_type='image/jpeg')
    return f"gs://{bucket_name}/{destination_blob_name}"

@app.route('/predict', methods=['POST'])
def predict():
    """
    Endpoint untuk prediksi gambar dan menyimpan hasil ke bucket.
    """
    global predicted_result
    if 'image' not in request.files:
        return jsonify({"error": "No file part"}), 400
    uploaded_image = request.files['image']
    if uploaded_image.filename == '':
        return jsonify({"error": "No selected file"}), 400

    # Prediksi kelas
    predicted_class = predict_image(uploaded_image)

    # Simpan gambar ke bucket
    uploaded_image.seek(0)  # Reset stream ke awal sebelum membaca konten
    image_content = uploaded_image.read()  # Baca konten file gambar
    destination_blob_name = f"predictions/{predicted_class}/{uploaded_image.filename}"  # Struktur path di bucket
    bucket_url = upload_to_bucket(BUCKET_NAME, image_content, destination_blob_name)

    # Ambil data dari Firestore
    document_data = get_document_data(predicted_class)

    # Gabungkan hasil prediksi, data Firestore, dan URL bucket
    predicted_result = {
        "predicted_class": predicted_class,
        "document_data": document_data,
        "image_url": bucket_url
    }
    return jsonify(predicted_result), 201

@app.route('/result/<field>', methods=['GET'])
def get_field(field):
    """
    Endpoint untuk mengambil data berdasarkan field tertentu dari hasil prediksi terakhir.
    """
    if field in predicted_result:
        return jsonify({field: predicted_result[field]}), 200
    elif field in predicted_result.get('document_data', {}):
        return jsonify({field: predicted_result['document_data'][field]}), 200
    else:
        return jsonify({"error": f"Field '{field}' not found"}), 404

@app.route('/get_document/<doc_id>/<field>', methods=['GET'])
def get_document(doc_id, field):
    """
    Endpoint untuk mengambil data dari Firestore berdasarkan dokumen dan field tertentu.
    """
    doc_ref = db.collection('makanan').document(doc_id)
    doc = doc_ref.get()
    if not doc.exists:
        return jsonify({"error": f"Document with id '{doc_id}' not found"}), 404

    document_data = doc.to_dict()
    if field in document_data:
        return jsonify({field: document_data[field]}), 200
    else:
        return jsonify({"error": f"Field '{field}' not found in document '{doc_id}'"}), 404

@app.route('/get_document/<doc_id>', methods=['GET'])
def get_full_document(doc_id):
    """
    Endpoint untuk mengambil semua data dari dokumen Firestore berdasarkan ID.
    """
    doc_ref = db.collection('makanan').document(doc_id)
    doc = doc_ref.get()
    if not doc.exists:
        return jsonify({"error": f"Document with id '{doc_id}' not found"}), 404

    return jsonify(doc.to_dict()), 200


if __name__ == '__main__':
    app.run(debug=True)
