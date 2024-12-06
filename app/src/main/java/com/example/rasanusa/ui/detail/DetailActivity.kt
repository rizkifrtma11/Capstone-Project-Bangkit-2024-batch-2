package com.example.rasanusa.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rasanusa.data.response.DataItem
import com.example.rasanusa.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDetail()

        binding.btnExit.setOnClickListener { finish() }
    }

    private fun getDetail(){
        val foodItem = intent.getParcelableExtra<DataItem>(FOOD_ITEM)
        showLoading(true)
        if (foodItem != null){
            showLoading(false)
            foodDetails(foodItem)
        }else {
            showLoading(false)
            showError()
        }
    }

    private fun foodDetails(foodItem: DataItem) {
        Glide.with(this@DetailActivity)
            .load(foodItem.image)
            .into(binding.ivFood)

        // Data
        binding.txtTitleFoodName.text = foodItem.name
        binding.txtAsalDaerah.text = foodItem.asal
        binding.txtBahanDasar.text = foodItem.bahanDasar
        binding.txtFoodDesc.text = foodItem.desc
        binding.txtFoodHistory.text = foodItem.history

        // Data.funfact
        binding.txtFunfact.text = foodItem.funfact

        // Gizi
        foodItem.gizi?.let {
            binding.txtCalPerServing.text = it.servingSize
            binding.txtJmlKalori.text = it.calories
            binding.txtJmlKarbohidrat.text = it.carbohidrate
            binding.txtJmlLemak.text = it.fat
            binding.txtJmlProtein.text = it.protein
        }
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError() {
        Toast.makeText(this, "Data makanan tidak ditemukan.", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        const val FOOD_ITEM = "food_item"
    }
}