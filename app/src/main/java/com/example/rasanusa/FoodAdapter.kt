package com.example.rasanusa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private val listFood: ArrayList<Food>): RecyclerView.Adapter<FoodAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, asalDaerah, photo) = listFood[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.txtAsalDaerah.text = asalDaerah
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_food)
        val tvName: TextView = itemView.findViewById(R.id.txt_food_name)
        val txtAsalDaerah: TextView = itemView.findViewById(R.id.txt_asal_daerah)
    }
}

