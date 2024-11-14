import firebase_admin
from firebase_admin import credentials, firestore
from flask import Flask, jsonify, request
import bcrypt
import os

# Inisialisasi Firebase Admin SDK
cred = credentials.Certificate('rasanusa.json')  # Ganti dengan path ke file credential Anda
firebase_admin.initialize_app(cred)

# Inisialisasi Firestore
db = firestore.client()

app = Flask(__name__)

# Fungsi untuk mengambil data dari Firestore berdasarkan nama dokumen di koleksi 'makanan'
def get_data_from_firestore(document_name):
    """
    Fungsi untuk mengambil data dari Firestore berdasarkan nama dokumen dalam koleksi 'makanan'.
    """
    if not document_name:
        return {"error": "Harap berikan parameter 'document_name'"}
    doc_ref = db.collection('makanan').document(document_name)
    doc = doc_ref.get()
    if doc.exists:
        data = doc.to_dict()
        return data
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

# Endpoint untuk mendapatkan username berdasarkan email
@app.route('/get_username', methods=['GET'])
def get_username():
    """
    Endpoint untuk mendapatkan username berdasarkan email.
    """
    email = request.args.get('email')
    if not email:
        return jsonify({"error": "Harap berikan parameter 'email'"}), 400
    
    # Cari user berdasarkan email
    users_ref = db.collection('user')
    query = users_ref.where('email', '==', email).get()

    if not query:
        return jsonify({"error": "User tidak ditemukan"}), 404

    # Ambil field 'username' dari user yang cocok
    user_data = query[0].to_dict()
    username = user_data.get('username')
    return jsonify({"username": username})

# Endpoint untuk mengizinkan akses kamera dan memproses data pemindaian
@app.route('/allow_camera', methods=['POST'])
def allow_camera():
    try:
        data = request.json
        scan_data = data.get('scan_data')
        if not scan_data:
            return jsonify({"error": "Data scan tidak diberikan"}), 400
        return jsonify({"message": "Data scan dari kamera diterima", "scan_data": scan_data}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Endpoint untuk mengizinkan akses galeri dan upload foto
@app.route('/allow_gallery', methods=['POST'])
def allow_gallery():
    try:
        if 'image' not in request.files:
            return jsonify({"error": "Tidak ada file gambar yang diberikan"}), 400
        image = request.files['image']
        upload_dir = 'uploads'
        os.makedirs(upload_dir, exist_ok=True)
        image_path = os.path.join(upload_dir, image.filename)
        image.save(image_path)
        return jsonify({"message": "Gambar berhasil diunggah", "file_path": image_path}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
