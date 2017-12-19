package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Main {

    @SerializedName("temp")
    var temp: Double = 0.toDouble()

    @SerializedName("temp_min")
    var tempMin: Double = 0.toDouble()

    @SerializedName("humidity")
    var humidity: Int = 0

    @SerializedName("pressure")
    var pressure: Double = 0.toDouble()

    @SerializedName("temp_max")
    var tempMax: Double = 0.toDouble()

    override fun toString(): String {
        return "Main{" +
                "temp = '" + temp + '\'' +
                ",temp_min = '" + tempMin + '\'' +
                ",humidity = '" + humidity + '\'' +
                ",pressure = '" + pressure + '\'' +
                ",temp_max = '" + tempMax + '\'' +
                "}"
    }
}