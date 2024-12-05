package com.example.rasanusa.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rasanusa.MainActivity
import com.example.rasanusa.R
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistory
import com.example.rasanusa.data.response.DataItem
import com.example.rasanusa.databinding.ActivityHistoryBinding
import com.example.rasanusa.ui.adapter.HistoryAdapter
import com.example.rasanusa.ui.detail.DetailActivity

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    private val historyViewModel : HistoryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel.listHistoryFood.observe(this){ historyResponse ->
            val histories = historyResponse ?: emptyList()
            setupRecyclerViewHistory(histories)
        }
        historyViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        historyViewModel.getPredictionHistory(this)

        binding.apply {
            bntBack.setOnClickListener {
                val intent = Intent(this@HistoryActivity, MainActivity::class.java)
                intent.putExtra("navigate_to", "ProfileFragment")
                startActivity(intent)
                finish()
            }
            deleteHistory.setOnClickListener{ showDialogDeleteHistory() }
        }

//        onBackPressedDispatcher.onBackPressed()

    }

    private fun setupRecyclerViewHistory(foodHistory: List<FoodHistory>){
        historyAdapter = HistoryAdapter(foodHistory){ selectedFood ->
            val dataItem = DataItem(
                image = selectedFood.predictionResult.documentData?.image,
                name = selectedFood.predictionResult.documentData?.name,
                bahanDasar = selectedFood.predictionResult.documentData?.bahanDasar,
                asal = selectedFood.predictionResult.documentData?.asal,
                history = selectedFood.predictionResult.documentData?.history,
                desc = selectedFood.predictionResult.documentData?.desc,
                funfact = selectedFood.predictionResult.documentData?.funfact,
                gizi = selectedFood.predictionResult.documentData?.gizi
            )
            navigateToDetail(dataItem)
        }

        binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvHistory.adapter = historyAdapter

    }

    private fun showDialogDeleteHistory(){

        AlertDialog.Builder(this)
            .setTitle(R.string.delete_history)
            .setMessage(R.string.delete_all_history)
            .setPositiveButton(R.string.setuju){_, _, ->
                historyViewModel.deleteAllHistory()
                Toast.makeText(this@HistoryActivity, R.string.history_deleted, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.tidak, null).show()
    }

    private fun navigateToDetail(itemFood: DataItem){
        val intentDetail = Intent(this@HistoryActivity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.FOOD_ITEM, itemFood)
        }
        startActivity(intentDetail)
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}