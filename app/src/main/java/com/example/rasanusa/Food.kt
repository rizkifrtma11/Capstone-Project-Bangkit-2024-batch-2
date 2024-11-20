package com.example.rasanusa

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food (
    val name: String,
    val asal_daerah: String,
    val photo: Int
): Parcelable