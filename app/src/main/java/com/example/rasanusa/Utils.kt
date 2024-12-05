package com.example.rasanusa

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
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
//    val filesDir = context.externalCacheDir
//    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun String.withCurrencyFormat(): String {
    val rupiahExchangeRate = 15898.69
    val usdExchangeRate = 0.000063

    val priceOnDollar = this.toDoubleOrNull() ?: return "$0.00" // Mengembalikan $0.00 jika konversi gagal

    var mCurrencyFormat = NumberFormat.getCurrencyInstance()
    val deviceLocale = Locale.getDefault().country

    when {
        deviceLocale.equals("US", ignoreCase = true) -> {
            val priceInUSD = priceOnDollar * usdExchangeRate
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
            return mCurrencyFormat.format(priceInUSD)
        }
        deviceLocale.equals("ID", ignoreCase = true) -> {
            val priceInIDR = priceOnDollar * rupiahExchangeRate
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            return mCurrencyFormat.format(priceInIDR)
        }
        else -> {
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
            return mCurrencyFormat.format(priceOnDollar)
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
