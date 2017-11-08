package com.tronography.pixelweather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import javax.inject.Inject;


public class SharedPrefsUtils {

    public static final String LAST_CITY_QUERIED_KEY = "last_city_queried";
    private SharedPreferences prefs;

    @Inject
    public SharedPrefsUtils(Context context) {
        prefs = getSharedPreferences(context);
    }

    private String getPrefsData(String key, @Nullable String defValue) {
        return prefs.getString(key, defValue);
    }

    private void setSharedPreferencesData(SharedPreferences prefs, String key, String defValue) {
        prefs.edit()
                .putString(key, defValue)
                .apply();
    }

    public void setLastCityQueried(String query) {
        System.out.println("setLastCityQueried = " + query);
        setSharedPreferencesData(prefs, LAST_CITY_QUERIED_KEY, query);
    }

    public String getLastCityQueried() {
        return getPrefsData(LAST_CITY_QUERIED_KEY, null);
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }
}
