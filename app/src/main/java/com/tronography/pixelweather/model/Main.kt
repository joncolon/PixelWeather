package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Main(
        @SerializedName("temp_max") var tempMax: Double,
        @SerializedName("pressure") var pressure: Double,
        @SerializedName("humidity") var humidity: Int,
        @SerializedName("temp_min") var tempMin: Double,
        @SerializedName("temp") var temp: Double
)