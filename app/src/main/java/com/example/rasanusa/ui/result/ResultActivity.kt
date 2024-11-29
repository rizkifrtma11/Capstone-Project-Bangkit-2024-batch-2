package com.example.rasanusa.ui.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.rasanusa.MainActivity
import com.example.rasanusa.R
import com.example.rasanusa.data.api.ApiConfig
import com.example.rasanusa.data.response.FoodResponse
import com.example.rasanusa.databinding.ActivityResultBinding
import com.example.rasanusa.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback

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
                val intent = Intent(this@ResultActivity, MainActivity::class.java)
                intent.putExtra("navigate_to", "ScanFragment")
                startActivity(intent)
                finish()
            }

            btnToDetail.setOnClickListener {
                Toast.makeText(this@ResultActivity, "Anda akan diarahkan ke Detail Activity", Toast.LENGTH_SHORT).show()
//                val predictedClass = binding.txtPredictedClass.text.toString()
//                fetchFoodDetails(predictedClass)
            }
        }
    }

    private fun getPredict(){
        val imageUrl = intent.getStringExtra(IMAGE_URL)
        val predictedClass = intent.getStringExtra(PREDICTED_CLASS)
        val confidenceScore = intent.getStringExtra(CONFIDENCE_SCORE)

        showLoading(true)
        if (imageUrl != null && predictedClass != null && confidenceScore != null){
            showLoading(false)
            Toast.makeText(this@ResultActivity, "Makanan anda berhasil dianalisis sebagai $predictedClass", Toast.LENGTH_SHORT).show()
            showResultDetail(imageUrl, predictedClass, confidenceScore)
        }else {
            showLoading(false)
            showError()
            finish()
        }
    }

    private fun showResultDetail(imageUrl: String?, predictedClass: String, confidence: String?){
        Glide.with(this@ResultActivity)
            .load(imageUrl)
            .into(binding.imageResult)

        binding.txtPredictedClass.text = predictedClass
        binding.txtConfidenceScore.text = confidence

    }

//    private fun fetchFoodDetails(predictedClass: String) {
//        val client = ApiConfig.getApiService()
//        client.getDetail(predictedClass).enqueue(object : Callback<FoodResponse> {
//            override fun onResponse(
//                call: Call<FoodResponse>,
//                response: retrofit2.Response<FoodResponse>) {
//                Log.d("API Response", "Response Body: ${response.body()}")
//                if (response.isSuccessful && response.body() != null) {
//                    val foodItem = response.body()
//                    if (foodItem != null) {
//                        navigateToDetail(foodItem)
//                    } else {
//                        Toast.makeText(
//                            this@ResultActivity,
//                            "Makanan tidak ditemukan",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    Toast.makeText(
//                        this@ResultActivity,
//                        "Makanan tidak ditemukan",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
//                Log.e("API Error", "Error fetching data: ${t.message}", t)
//                Toast.makeText(this@ResultActivity, "Gagal mengambil data: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun navigateToDetail(foodItem: FoodResponse?) {
//        val intentDetail = Intent(this@ResultActivity, DetailActivity::class.java).apply {
//            putExtra(DetailActivity.FOOD_RESULT, foodItem)
//        }
//        startActivity(intentDetail)
//    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError() {
        Toast.makeText(this, "Data makanan tidak ditemukan", Toast.LENGTH_SHORT).show()

    }

    companion object{
        const val IMAGE_URL = "image_url"
        const val PREDICTED_CLASS = "predicted_class"
        const val CONFIDENCE_SCORE = "confidence_score"

    }
}