package com.example.rasanusa.ui.imagepreview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rasanusa.ui.mainactivity.MainActivity
import com.example.rasanusa.R
import com.example.rasanusa.data.api.ApiConfig
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistory
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistoryRoomDatabase
import com.example.rasanusa.data.response.PredictResponse
import com.example.rasanusa.databinding.ActivityImagePreviewBinding
import com.example.rasanusa.helper.ScanPreferences
import com.example.rasanusa.ui.result.ResultActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class ImagePreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagePreviewBinding
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getImage()

        binding.apply {
            btnExit.setOnClickListener {
                val intent = Intent(this@ImagePreviewActivity, MainActivity::class.java).apply {
                    putExtra("navigate_to", "ScanFragment")
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish()
            }
            btnAnalyze.setOnClickListener {
                if (ScanPreferences.isScanLimitReached(this@ImagePreviewActivity)) {
                    showSubscriptionPopup()
                } else {
                    handleScan()
                    uploadImage()
                    Toast.makeText(this@ImagePreviewActivity, "Makanan sedang dianalisis...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getImage(){
        val imageUri = intent.getStringExtra("imageUri")
        if (imageUri != null) {
            uri = Uri.parse(imageUri)
            showImage(uri)
        } else {
            Toast.makeText(this, "Gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun uploadImage() {
        showLoading(true)

        val file = uriToFile(uri, this)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val apiService = ApiConfig.getApiService()
        apiService.uploadImage(imagePart).enqueue(object : Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                showLoading(false)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    val db = FoodHistoryRoomDatabase.getDatabase(applicationContext)

                    CoroutineScope(Dispatchers.IO).launch {
                        db.foodHistoryDao().insert(
                            FoodHistory(
                                imagePredict = uri,
                                predictionResult = result,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        withContext(Dispatchers.Main){
                            navigateToResult(result)
                        }
                    }
                } else {
                    Toast.makeText(this@ImagePreviewActivity, "Gagal menganalisis gambar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@ImagePreviewActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToResult(predictResponse: PredictResponse) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(ResultActivity.IMAGE_URL, uri.toString())
            putExtra(ResultActivity.PREDICTED_CLASS, predictResponse.documentData?.name)
            putExtra(ResultActivity.CONFIDENCE_SCORE, predictResponse.confidence)
            putExtra(ResultActivity.DOCUMENT_DATA, predictResponse.documentData)
        }
        startActivity(intent)
        finish()
    }

    private fun uriToFile(selectedUri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val myFile = File(context.cacheDir, "temp_image.jpg")

        contentResolver.openInputStream(selectedUri)?.use { inputStream ->
            FileOutputStream(myFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return myFile
    }

    private fun showImage(uri: Uri) {
        binding.imageResult.setImageURI(uri)
        Log.d("Image URI", "showImage: $uri")
    }

    private fun handleScan() {
        if (ScanPreferences.isScanLimitReached(this)) {
            showSubscriptionPopup()
        } else {
            ScanPreferences.incrementScanCount(this)
        }
    }

    private fun showSubscriptionPopup() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_limit))
            .setMessage(getString(R.string.message_limit))
            .setPositiveButton(getString(R.string.subscription_now_button)) { _, _ ->
                val intent = Intent(this@ImagePreviewActivity, MainActivity::class.java)
                intent.putExtra("navigate_to", "SubscriptionFragment")
                startActivity(intent)
                finish()
            }
            .setNegativeButton(R.string.tidak, null)
            .show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}