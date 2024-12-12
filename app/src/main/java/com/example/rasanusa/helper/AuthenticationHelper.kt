package com.example.rasanusa.helper

import android.content.Context

object AuthenticationHelper {

    private const val PREF_NAME = "UserPrefs"

    fun saveUsername(context: Context, email: String, username: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("USERNAME_$email", username)
            apply()
        }
    }

    fun getUsername(context: Context, email: String): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("USERNAME_$email", "User") ?: "User"
    }

}