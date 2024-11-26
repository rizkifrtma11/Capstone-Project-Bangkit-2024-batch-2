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
cred = credentials.Certificate('firebase.json')  # Pastikan file firebase.json ada
firebase_admin.initialize_app(cred)
db = firestore.client()

# Konfigurasi Google Cloud Storage
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "bucket.json"
BUCKET_NAME = "rasanusa"

# Konfigurasi model dan ukuran gambar
IMAGE_SIZE = (224, 224)
MODEL_PATH = "classification_model.h5"  # Lokasi model lokal

# Variabel global untuk hasil prediksi
predicted_result = {}

# Fungsi untuk memproses gambar
def load_and_preprocess_image(uploaded_image):
    img = Image.open(io.BytesIO(uploaded_image.read()))
    img = img.convert('RGB').resize(IMAGE_SIZE)
    img = np.array(img) / 255.0
    return np.expand_dims(img, axis=0)

# Fungsi untuk prediksi
def predict_image(uploaded_image):
    img = load_and_preprocess_image(uploaded_image)
    predictions = model.predict(img)
    class_labels = ['kerak_telor', 'mie_aceh', 'papeda', 'pempek', 'soto_banjar', 'tahu_sumedang', 
                    'bika_ambon', 'rendang', 'sate_lilit', 'es_pisang_ijo', 'lumpia']
    return class_labels[np.argmax(predictions)]

# Fungsi untuk mengambil data dari Firestore
def get_document_data(collection, doc_id):
    doc_ref = db.collection(collection).document(doc_id)
    doc = doc_ref.get()
    if doc.exists:
        return doc.to_dict()
    return {"error": f"Document '{doc_id}' not found"}

# Endpoint Root
@app.route('/', methods=['GET'])
def index():
    return jsonify({"status": "success", "message": "RasaNusa API is running..."}), 200

# Endpoint Register User
@app.route('/register', methods=['POST'])
def register():
    try:
        data = request.get_json()
        email = data.get('email')
        password = data.get('password')
        username = data.get('username')

        if not all([email, password, username]):
            return jsonify({"status": "fail", "message": "Email, password, and username are required"}), 400

        user = auth.create_user(email=email, password=password)
        db.collection('users').document(user.uid).set({
            'username': username,
            'email': email,
            'created_at': firestore.SERVER_TIMESTAMP
        })

        return jsonify({"status": "success", "message": "User registered successfully", "uid": user.uid}), 201
    except Exception as e:
        return jsonify({"status": "fail", "message": str(e)}), 500

# Endpoint Predict
@app.route('/predict', methods=['POST'])
def predict():
    if 'image' not in request.files:
        return jsonify({"status": "fail", "message": "No image file found"}), 400
    uploaded_image = request.files['image']

    try:
        predicted_class = predict_image(uploaded_image)
        uploaded_image.seek(0)
        image_content = uploaded_image.read()
        destination_blob_name = f"predictions/{predicted_class}/{uploaded_image.filename}"

        # Upload ke Google Cloud Storage
        storage_client = storage.Client()
        bucket = storage_client.bucket(BUCKET_NAME)
        blob = bucket.blob(destination_blob_name)
        blob.upload_from_string(image_content, content_type='image/jpeg')

        # Ambil data dari Firestore
        document_data = get_document_data('makanan', predicted_class)

        # Simpan hasil ke Firestore
        db.collection('scan_history').add({
            'predicted_class': predicted_class,
            'image_url': f"gs://{BUCKET_NAME}/{destination_blob_name}",
            'document_data': document_data,
            'timestamp': firestore.SERVER_TIMESTAMP
        })

        return jsonify({
            "status": "success",
            "predicted_class": predicted_class,
            "image_url": f"gs://{BUCKET_NAME}/{destination_blob_name}",
            "document_data": document_data
        }), 201
    except Exception as e:
        return jsonify({"status": "fail", "message": str(e)}), 500

# Endpoint Get History
@app.route('/history', methods=['GET'])
def get_history():
    try:
        docs = db.collection('scan_history').stream()
        history = [doc.to_dict() for doc in docs]
        return jsonify({"status": "success", "history": history}), 200
    except Exception as e:
        return jsonify({"status": "fail", "message": str(e)}), 500

# Endpoint Get Specific Field
@app.route('/result/<field>', methods=['GET'])
def get_field(field):
    if field in predicted_result:
        return jsonify({"status": "success", field: predicted_result[field]}), 200
    elif field in predicted_result.get('document_data', {}):
        return jsonify({"status": "success", field: predicted_result['document_data'][field]}), 200
    return jsonify({"status": "fail", "message": f"Field '{field}' not found"}), 404

# Endpoint Get Full Document
@app.route('/makanan/<doc_id>', methods=['GET'])
def get_full_document(doc_id):
    try:
        document_data = get_document_data('makanan', doc_id)
        if "error" in document_data:
            return jsonify({"status": "fail", "message": document_data["error"]}), 404
        return jsonify({"status": "success", "data": document_data}), 200
    except Exception as e:
        return jsonify({"status": "fail", "message": str(e)}), 500

@app.route('/makanan/<doc_id>/<field>', methods=['GET'])
def get_document_field(doc_id, field):
    try:
        # Ambil dokumen dari Firestore
        document_data = get_document_data('makanan', doc_id)

        if "error" in document_data:
            return jsonify({"status": "fail", "message": document_data["error"]}), 404

        # Cek apakah field yang diminta ada dalam dokumen
        if field in document_data:
            return jsonify({"status": "success", field: document_data[field]}), 200

        return jsonify({"status": "fail", "message": f"Field '{field}' not found in document '{doc_id}'"}), 404
    except Exception as e:
        return jsonify({"status": "fail", "message": str(e)}), 500

# --- Muat Model ---
if not os.path.exists(MODEL_PATH):
    raise FileNotFoundError(f"Model file '{MODEL_PATH}' not found. Ensure the file exists in the current directory.")
model = tf.keras.models.load_model(MODEL_PATH)

# Jalankan Aplikasi Flask
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=int(os.environ.get('PORT', 8080)))
