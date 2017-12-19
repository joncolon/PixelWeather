package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class WeatherItem(
        @SerializedName("id") var id: Int,
        @SerializedName("main") var main: String,
        @SerializedName("description") var description: String,
        @SerializedName("icon") var icon: String
)