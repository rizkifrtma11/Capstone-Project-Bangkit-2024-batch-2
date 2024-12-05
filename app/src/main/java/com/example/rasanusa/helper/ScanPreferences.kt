package com.example.rasanusa.helper

import android.content.Context

object ScanPreferences {
    private const val PREFS_NAME = "ScanPrefs"
    private const val SCAN_COUNT_KEY = "scan_count"
    private const val SCAN_LIMIT = 3

    fun incrementScanCount(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentCount = prefs.getInt(SCAN_COUNT_KEY, 0)
        prefs.edit().putInt(SCAN_COUNT_KEY, currentCount + 1).apply()
    }

    fun getScanCount(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(SCAN_COUNT_KEY, 0)
    }

    fun resetScanCount(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(SCAN_COUNT_KEY, 0).apply()
    }

    fun isScanLimitReached(context: Context): Boolean {
        return getScanCount(context) >= SCAN_LIMIT
    }
}
