package com.example.rasanusa.data

import com.example.rasanusa.data.response.Gizi

data class FoodItem (
    val name: String?,
    val asal: String?,
    val bahanDasar: String?,
    val desc: String?,
    val funfact: String?,
    val history: String?,
    val gizi: Gizi?,
    val imageUrl: String?
)
