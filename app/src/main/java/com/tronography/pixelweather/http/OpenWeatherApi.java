package com.tronography.pixelweather.http;


import com.tronography.pixelweather.BuildConfig;

public class OpenWeatherApi {

    private static final String API_BASE_URL = "http://api.openweathermap.org/";
    private static final String API_KEY = "464338f6c8e2fe75eeef33eda895e105";

    public static String getBaseUrl() {
        return API_BASE_URL;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}