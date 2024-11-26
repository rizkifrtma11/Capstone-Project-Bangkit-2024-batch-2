# **RasaNusa API Documentation**

## **Table of Contents**
1. [Root Endpoint](#root-endpoint)
2. [Register](#1-register)
3. [Predict](#2-predict)
4. [History](#3-history)
5. [Result Field](#4-result-field)
6. [Data Makanan Spesifik](#5-data-makanan-spesifik)
7. [Dokumen Makanan Lengkap](#6-dokumen-makanan-lengkap)

# **URL**
<ul>RasaNusa API</ul>
<ul>https://rasanusa-api-555896629878.asia-southeast2.run.app</ul>

---

## **Root Endpoint**
- **Endpoint:** `/`
- **Method:** `GET`
- **Description:** Endpoint dasar untuk memastikan API berjalan.
- **Response:**
    ```json
    {
        "message": "RasaNusa api is running..."
    }
    ```

---

## **1. Register**
- **Endpoint:** `/register`
- **Method:** `POST`
- **Description:** Mendaftarkan pengguna baru.
- **Request Body (JSON):**
    ```json
    {
        "email": "string",
        "password": "string",
        "username": "string"
    }
    ```
- **Successful Response:**
    ```json
    {
        "message": "User registered successfully",
        "uid": "string",
        "username": "string"
    }
    ```
- **Error Response:**
    ```json
    {
        "error": "string"
    }
    ```

---

## **2. Predict**
- **Endpoint:** `/predict`
- **Method:** `POST`
- **Description:** Mengunggah gambar dan mendapatkan prediksi makanan.
- **Form Data:**
    - `image` (*file*) - Gambar makanan.
- **Successful Response:**
    ```json
    {
        "predicted_class": "string",
        "document_data": {
            "key": "value"
        },
        "image_url": "string"
    }
    ```
- **Error Response:**
    ```json
    {
        "error": "No file part"
    }
    ```

---

## **3. History**
- **Endpoint:** `/history`
- **Method:** `GET`
- **Description:** Mendapatkan daftar riwayat prediksi.
- **Successful Response:**
    ```json
    [
        {
            "predicted_class": "string",
            "document_data": {
                "key": "value"
            },
            "image_url": "string",
            "timestamp": "string"
        }
    ]
    ```
- **Error Response:**
    ```json
    {
        "error": "string"
    }
    ```

---

## **4. Result Field**
- **Endpoint:** `/result/<field>`
- **Method:** `GET`
- **Description:** Mengambil detail spesifik dari hasil prediksi terakhir.
- **Parameters:**
    - `<field>` (*string*) - Nama field yang ingin diambil.
- **Successful Response:**
    ```json
    {
        "field_name": "value"
    }
    ```
- **Error Response:**
    ```json
    {
        "error": "Field '<field>' not found"
    }
    ```

---

## **5. Data Makanan Spesifik**
- **Endpoint:** `/makanan/<doc_id>/<field>`
- **Method:** `GET`
- **Description:** Mengambil field spesifik dari data makanan di Firestore.
- **Parameters:**
    - `<doc_id>` (*string*) - ID dokumen makanan.
    - `<field>` (*string*) - Nama field yang ingin diambil.
- **Successful Response:**
    ```json
    {
        "field_name": "value"
    }
    ```
- **Error Response:**
    ```json
    {
        "error": "Field '<field>' not found in document '<doc_id>'"
    }
    ```

---

## **6. Dokumen Makanan Lengkap**
- **Endpoint:** `/makanan/<doc_id>`
- **Method:** `GET`
- **Description:** Mengambil data lengkap dari dokumen makanan.
- **Parameters:**
    - `<doc_id>` (*string*) - ID dokumen makanan.
- **Successful Response:**
    ```json
    {
        "key": "value"
    }
    ```
- **Error Response:**
    ```json
    {
        "error": "Document not found"
    }
    ```

---

## **Additional Notes**
- Semua data yang dikembalikan dari Firestore akan berbentuk JSON.
- Timestamps dalam respons berupa format ISO (`YYYY-MM-DDTHH:MM:SS.sssZ`).
- Gambar diunggah ke Google Cloud Storage dan URL gambar dikembalikan dalam respons.
