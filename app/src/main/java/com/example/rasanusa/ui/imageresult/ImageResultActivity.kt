package com.example.rasanusa.ui.imageresult

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rasanusa.R
import com.example.rasanusa.databinding.ActivityImageResultBinding

class ImageResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra("imageUri")
        val uri = Uri.parse(imageUri)

        showImage(uri)
        analyzeImage(uri)
    }

    private fun analyzeImage(uri: Uri) {
        binding.btnAnalyze.setOnClickListener {
            Toast.makeText(this, "Makanan sedang di analisis", Toast.LENGTH_SHORT).show()
            Log.d("AnalysisActivity", "Analyzing image at: $uri")
        }
    }

    fun showImage(uri: Uri) {
        binding.imageResult.setImageURI(uri)
        Log.d("Image URI", "showImage: $uri")
    }
}