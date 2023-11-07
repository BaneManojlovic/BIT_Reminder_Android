package com.example.bitreminder.Utils

import android.content.Context
import com.example.bitreminder.LoginScreen.UserModel

class SharedPreferencesHelper(private val context: Context) {

    companion object {
        private const val MY_PREF_KEY = "MY_PREF"
    }

    fun saveUserName(key: String, data: UserModel) {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, data.user_name).apply()
    }

    fun getUserName(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun saveUserEmail(key: String, data: UserModel) {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, data.user_email).apply()
    }

    fun getUserEmail(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun saveStringData(key: String, data: String) {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, data).apply()
    }

    fun getStringData(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun clearPreferences() {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}