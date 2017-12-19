package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Coord {

    @SerializedName("lon")
    var lon: Double = 0.toDouble()

    @SerializedName("lat")
    var lat: Double = 0.toDouble()
}