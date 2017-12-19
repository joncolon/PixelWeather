package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse(
        @SerializedName("dt") var dt: Int,
        @SerializedName("weather") var weather: List<WeatherItem>,
        @SerializedName("name") var name: String,
        @SerializedName("main") var main: Main,
        @SerializedName("clouds") var clouds: Clouds,
        @SerializedName("id") var id: Int,
        @SerializedName("sys") var sys: Sys,
        @SerializedName("wind") var wind: Wind
)