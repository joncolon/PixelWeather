package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Wind(
        @SerializedName("deg") var deg: Double,
        @SerializedName("speed") var speed: Double = 0.toDouble()
)