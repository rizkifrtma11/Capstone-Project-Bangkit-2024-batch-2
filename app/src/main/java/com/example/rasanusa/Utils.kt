package com.example.rasanusa

import android.content.Context
import android.os.Environment
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String get() {
    val dateFormat = SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date())
}

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
//    val filesDir = context.externalCacheDir
//    return File.createTempFile(timeStamp, ".jpg", filesDir)
}