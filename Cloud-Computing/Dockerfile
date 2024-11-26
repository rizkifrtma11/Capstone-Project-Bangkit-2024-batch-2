FROM python:3.9-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# Download model saat build image
RUN python download_model.py

CMD exec gunicorn --bind :$PORT --workers 1 --threads 8 main:app