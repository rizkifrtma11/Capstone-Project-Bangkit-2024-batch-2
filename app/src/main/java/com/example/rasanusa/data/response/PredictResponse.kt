package com.example.rasanusa.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PredictResponse(

	@field:SerializedName("document_data")
	val documentData: DocumentData? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("confidence")
	val confidence: String? = null,

	@field:SerializedName("predicted_class")
	val predictedClass: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

) : Parcelable


@Parcelize
data class DocumentData(
	@field:SerializedName("key")
	val key: String? = null,

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
