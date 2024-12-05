package com.example.rasanusa.data.localdatabase

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rasanusa.data.response.PredictResponse
import kotlinx.parcelize.Parcelize

@Entity(tableName = "foodHistory")
@Parcelize
data class FoodHistory(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val imagePredict : Uri,
    val predictionResult : PredictResponse,
    val timestamp : Long,
) : Parcelable