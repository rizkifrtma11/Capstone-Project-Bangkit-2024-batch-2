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
import com.example.rasanusa.data.response.DataItem



class FoodAdapter(
    private var foodList: List<DataItem>, private val onItemClicked: (DataItem) -> Unit) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val itemData = foodList[position]
        Glide.with(holder.itemView.context)
            .load(itemData.image)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
            .into(holder.imgPhoto)
        holder.name.text = itemData.name
        holder.asal.text = itemData.asal

        holder.itemView.setOnClickListener{
            onItemClicked(itemData)

        }

    }

    override fun getItemCount(): Int = foodList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_food)
        val name: TextView = itemView. findViewById(R.id.txt_food_name)
        val asal: TextView = itemView.findViewById(R.id.txt_asal_daerah)

    }
}

