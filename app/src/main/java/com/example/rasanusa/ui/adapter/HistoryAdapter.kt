package com.example.rasanusa.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.rasanusa.R
import com.example.rasanusa.data.localdatabase.FoodHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter(private var historyList: List<FoodHistory>, private val onItemClicked: (FoodHistory) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val itemData = historyList[position]
        Glide.with(holder.itemView.context)
            .load(itemData.imagePredict)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_place_holder)
                .transform(CenterCrop(), RoundedCorners(10)))
            .into(holder.imgPhoto)

        holder.name.text = itemData.predictionResult.documentData?.name ?: "Unknown"
        holder.timestamp.text = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            .format(Date(itemData.timestamp))

        holder.itemView.setOnClickListener {
            onItemClicked(itemData)
        }
    }

    override fun getItemCount(): Int = historyList.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.ivFoodScan)
        val name: TextView = itemView.findViewById(R.id.txt_food_name)
        val timestamp: TextView = itemView.findViewById(R.id.txt_timestamp)

    }
}