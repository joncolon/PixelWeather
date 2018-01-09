package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Main(
        @SerializedName("temp_max") var tempMax: Float,
        @SerializedName("pressure") var pressure: Float,
        @SerializedName("humidity") var humidity: Int,
        @SerializedName("temp_min") var tempMin: Float,
        @SerializedName("temp") var temp: Float
)