package com.tronography.pixelweather.utils

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject


class SharedPrefsUtils @Inject
constructor(context: Context) {
    private val prefs: SharedPreferences

    var lastCityQueried: String?
        get() = getPrefsData(LAST_CITY_QUERIED_KEY, null)
        set(query) {
            println("setLastCityQueried = " + query)
            setSharedPreferencesData(prefs, LAST_CITY_QUERIED_KEY, query)
        }

    init {
        prefs = getSharedPreferences(context)
    }

    private fun getPrefsData(key: String, defValue: String?): String? {
        return prefs.getString(key, defValue)
    }

    private fun setSharedPreferencesData(prefs: SharedPreferences, key: String, defValue: String?) {
        prefs.edit()
                .putString(key, defValue)
                .apply()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    companion object {

        private val LAST_CITY_QUERIED_KEY = "last_city_queried"
    }
}
