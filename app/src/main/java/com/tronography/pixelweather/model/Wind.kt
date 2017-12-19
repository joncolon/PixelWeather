package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Wind {

    @SerializedName("deg")
    var deg: Double = 0.toDouble()
        private set

    @SerializedName("speed")
    var speed: Double = 0.toDouble()

    fun setDeg(deg: Int) {
        this.deg = deg.toDouble()
    }

    override fun toString(): String {
        return "Wind{" +
                "deg = '" + deg + '\'' +
                ",speed = '" + speed + '\'' +
                "}"
    }
}