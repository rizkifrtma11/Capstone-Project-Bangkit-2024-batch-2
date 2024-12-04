package com.example.rasanusa.config

import android.net.Uri
import androidx.room.TypeConverter
import com.example.rasanusa.data.response.PredictResponse
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromPredictResponse(predictResponse: PredictResponse): String {
        return Gson().toJson(predictResponse)
    }

    @TypeConverter
    fun toPredictResponse(data: String): PredictResponse {
        return Gson().fromJson(data, PredictResponse::class.java)
    }

    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}