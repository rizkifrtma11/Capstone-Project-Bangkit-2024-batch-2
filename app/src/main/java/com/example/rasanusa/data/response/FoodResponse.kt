package com.example.rasanusa.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FoodResponse(

    @field:SerializedName("data")
    val data: List<DataItem>? = null,

    @field:SerializedName("status")
    val status: String? = null

) : Parcelable

@Parcelize
data class Gizi(

    @field:SerializedName("protein")
    val protein: String? = null,

    @field:SerializedName("serving_size")
    val servingSize: String? = null,

    @field:SerializedName("fat")
    val fat: String? = null,

    @field:SerializedName("calories")
    val calories: String? = null,

    @field:SerializedName("carbohidrate")
    val carbohidrate: String? = null
) : Parcelable

@Parcelize
data class DataItem(

    @field:SerializedName("asal")
    val asal: String? = null,

    @field:SerializedName("gizi")
    val gizi: Gizi? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("bahan_dasar")
    val bahanDasar: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("funfact")
    val funfact: String? = null,

    @field:SerializedName("history")
    val history: String? = null,

    @field:SerializedName("desc")
    val desc: String? = null

) : Parcelable