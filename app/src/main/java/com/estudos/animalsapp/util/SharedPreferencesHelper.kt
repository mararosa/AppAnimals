package com.estudos.animalsapp.util

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferencesHelper(context: Context) {

    private val PREFERENCE_API_KEY = "Api Key"
    private val prefes = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key: String?) {
        prefes.edit().putString(PREFERENCE_API_KEY, key).apply()
    }

    fun getApiKey() = prefes.getString(PREFERENCE_API_KEY, null)
}