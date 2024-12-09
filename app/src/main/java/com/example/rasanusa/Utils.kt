package com.example.rasanusa

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.TextView
import java.io.File
import java.text.NumberFormat
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
}

fun String.withCurrencyFormat(): String {
    val usdExchangeRate = 0.000063

    val priceValue = this.toDoubleOrNull() ?: return "Invalid price format"

    val mCurrencyFormat: NumberFormat
    val locale = Locale.getDefault()

    return when (locale.language.lowercase()) {
        "id" -> {
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            mCurrencyFormat.format(priceValue)
        }
        "en" -> {
            val priceInUSD = priceValue * usdExchangeRate
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
            mCurrencyFormat.format(priceInUSD)
        }
        else -> {
            mCurrencyFormat = NumberFormat.getCurrencyInstance(locale)
            mCurrencyFormat.format(priceValue)
        }
    }
}

fun setFormattedPrice(textView: TextView, price: String) {
    val formattedPrice = price.withCurrencyFormat()
    Log.d("FormattedPrice", "Formatted Price: $formattedPrice")

    textView.text = textView.context.getString(R.string.price, formattedPrice)
}

fun setStrikeThrough(textView: TextView) {
    textView.paintFlags = textView.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
}
