package com.example.quickmart.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    fun isDatabaseSeeded(): Boolean {
        return preferences.getBoolean("isDatabaseSeeded", false)
    }

    fun setDatabaseSeeded() {
        preferences.edit().putBoolean("isDatabaseSeeded", true).apply()
    }
}