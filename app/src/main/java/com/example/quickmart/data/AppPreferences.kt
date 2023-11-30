package com.example.quickmart.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean("isLoggedIn", false)
    }

    fun setLoggedIn() {
        preferences.edit().putBoolean("isLoggedIn", true).apply()
    }
}