package com.example.instagramclone.managers

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    val sharedPreference: SharedPreferences?

    init {
        sharedPreference = context.getSharedPreferences("insta_db", Context.MODE_PRIVATE)
    }

    fun storeDeviceToken(token: String?){
        val prefsEditor = sharedPreference!!.edit()
        prefsEditor.putString("device_token", token)
        prefsEditor.apply()
    }

    fun loadDeviceToken(): String? {
        return sharedPreference!!.getString("device_token", "")
    }

}