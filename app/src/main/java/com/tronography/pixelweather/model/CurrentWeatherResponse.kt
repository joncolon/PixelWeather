package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse(
        @SerializedName("dt") var dt: Int = 0,
        @SerializedName("weather") var weather: List<WeatherItem>? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("main") var main: Main? = null,
        @SerializedName("clouds") var clouds: Clouds? = null,
        @SerializedName("id") var id: Int = 0,
        @SerializedName("sys") var sys: Sys? = null,
        @SerializedName("wind") var wind: Wind? = null
)