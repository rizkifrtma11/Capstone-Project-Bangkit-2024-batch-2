package com.example.rasanusa.ui.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.rasanusa.ui.mainactivity.MainActivity
import com.example.rasanusa.R
import com.example.rasanusa.data.response.DocumentData
import com.example.rasanusa.databinding.ActivityResultBinding


@Suppress("DEPRECATION")
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPredict()

        binding.apply {
            btnExit.setOnClickListener {
                val intent = Intent(this@ResultActivity, MainActivity::class.java).apply {
                    putExtra("navigate_to", "ScanFragment")
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish()
            }

            btnToDetail.setOnClickListener {
                val documentData = intent.getParcelableExtra<DocumentData>(DOCUMENT_DATA)
                Log.d("ResultActivity", "Document Data: $documentData")

                if (documentData != null) {
                    val bottomSheet = BottomSheetDialogResultFragment.newInstance(documentData)
                    bottomSheet.show(supportFragmentManager, "BottomSheetDialogResultFragment")
                } else {
                    Log.e("ResultActivity", "Detail tidak tersedia: documentData is null")
                    Toast.makeText(this@ResultActivity, "Detail tidak tersedia", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getPredict() {
        val imageUrl = intent.getStringExtra(IMAGE_URL)
        val predictedClass = intent.getStringExtra(PREDICTED_CLASS)
        val confidenceScore = intent.getStringExtra(CONFIDENCE_SCORE)

        showLoading(true)

        if(imageUrl != null){
            showLoading(false)

            if (predictedClass.isNullOrEmpty() || confidenceScore.isNullOrEmpty()){
                resultUnknown(imageUrl)
            } else{
                showResultDetail(imageUrl, predictedClass, confidenceScore)
            }
        }else{
            showLoading(false)
            showError()
            finish()
        }
    }

    private fun showResultDetail(imageUrl: String?, predictedClass: String, confidence: String?) {
        Glide.with(this@ResultActivity)
            .load(imageUrl)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
            .into(binding.imageResult)

        binding.txtPredictedClass.text = predictedClass
        binding.txtConfidenceScore.text = confidence
    }

    private fun resultUnknown(imageUrl: String?) {
        Glide.with(this@ResultActivity)
            .load(imageUrl)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
            .into(binding.imageResult)

        binding.txtPredictedClass.text = getString(R.string.unknown_food)
        binding.txtConfidenceScore.text = getString(R.string.unknown_confidence)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError() {
        Toast.makeText(this, "Data makanan tidak ditemukan!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val IMAGE_URL = "image_url"
        const val PREDICTED_CLASS = "predicted_class"
        const val CONFIDENCE_SCORE = "confidence_score"
        const val DOCUMENT_DATA = "document_data"
    }
}