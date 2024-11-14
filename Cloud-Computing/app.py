import firebase_admin
from firebase_admin import credentials, firestore
from flask import Flask, jsonify, request
from google.cloud import storage
import bcrypt
import os

# Inisialisasi Firebase Admin SDK dengan firebase_admin.json
firebase_cred = credentials.Certificate('firebase.json')  # Ganti dengan path ke file firebase_admin.json Anda
firebase_admin.initialize_app(firebase_cred)

# Inisialisasi Firestore
db = firestore.client()

# Inisialisasi Google Cloud Storage Client dengan gcs_service_account.json
gcs_cred_path = 'bucket.json'  # Ganti dengan path ke file gcs_service_account.json Anda
storage_client = storage.Client.from_service_account_json(gcs_cred_path)
bucket_name = 'nyobain_capstone'  # Ganti dengan nama bucket Anda

app = Flask(__name__)

# Fungsi untuk mengambil data dari Firestore berdasarkan nama dokumen di koleksi 'makanan'
def get_data_from_firestore(document_name):
    """
    Fungsi untuk mengambil data dari Firestore berdasarkan nama dokumen dalam koleksi 'makanan'.
    """
    doc_ref = db.collection('makanan').document(document_name)
    doc = doc_ref.get()
    if doc.exists:
        return doc.to_dict()
    else:
        return {"error": "Dokumen tidak ditemukan"}

# Endpoint untuk mengambil data dari Firestore berdasarkan nama dokumen di koleksi 'makanan'
@app.route('/get_document', methods=['GET'])
def get_document():
    document_name = request.args.get('document_name')
    if not document_name:
        return jsonify({"error": "Harap berikan parameter 'document_name'"}), 400
    
    data = get_data_from_firestore(document_name)
    return jsonify(data)

# Endpoint untuk mendaftarkan user baru
@app.route('/register', methods=['POST'])
def register():
    """
    Endpoint untuk mendaftarkan user baru.
    Data harus dikirim dalam format JSON dengan kunci 'email', 'name', 'username', dan 'password'.
    """
    data = request.json
    email = data.get('email')
    name = data.get('name')
    username = data.get('username')
    password = data.get('password')

    if not email or not name or not username or not password:
        return jsonify({"error": "Harap berikan email, name, username, dan password"}), 400
    
    # Periksa apakah user dengan email yang sama sudah terdaftar
    users_ref = db.collection('user')
    query = users_ref.where('email', '==', email).get()

    if query:
        return jsonify({"error": "User dengan email ini sudah terdaftar"}), 400

    # Hash password sebelum disimpan
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

    # Tambahkan data ke Firestore
    new_user = {
        "email": email,
        "name": name,
        "username": username,
        "password": hashed_password
    }
    db.collection('user').document().set(new_user)
    return jsonify({"message": "Registrasi berhasil"}), 201

# Endpoint untuk login user
@app.route('/login', methods=['GET'])
def login():
    """
    Endpoint untuk login user.
    Data harus dikirimkan melalui parameter URL (?email=...) dan ?password=...
    """
    email = request.args.get('email')
    password = request.args.get('password')

    if not email or not password:
        return jsonify({"error": "Harap berikan parameter 'email' dan 'password'"}), 400
    
    # Cari user berdasarkan email
    users_ref = db.collection('user')
    query = users_ref.where('email', '==', email).get()

    if not query:
        return jsonify({"error": "User tidak ditemukan"}), 404

    # Ambil data user pertama yang cocok
    user_data = query[0].to_dict()
    stored_password = user_data.get('password')

    # Verifikasi password
    if bcrypt.checkpw(password.encode('utf-8'), stored_password.encode('utf-8')):
        return jsonify({"message": "Login berhasil", "user_data": {
            "email": user_data["email"],
            "name": user_data["name"],
            "username": user_data["username"]
        }})
    else:
        return jsonify({"error": "Password salah"}), 401

# Fungsi untuk mengunggah gambar ke Google Cloud Storage
def upload_to_bucket(file, bucket_name):
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(file.filename)
    blob.upload_from_file(file)
    # URL publik tanpa menggunakan make_public atau mengakses ACL
    return f"https://storage.googleapis.com/{bucket_name}/{file.filename}"

# Endpoint untuk mengizinkan akses galeri dan upload foto ke GCS
@app.route('/allow_gallery', methods=['POST'])
def allow_gallery():
    try:
        if 'image' not in request.files:
            return jsonify({"error": "Tidak ada file gambar yang diberikan"}), 400
        image = request.files['image']

        # Upload gambar ke Google Cloud Storage
        public_url = upload_to_bucket(image, bucket_name)

        return jsonify({"message": "Gambar berhasil diunggah", "file_url": public_url}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
