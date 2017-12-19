package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class ForecastResponse {

    @SerializedName("city")
    val city: City? = null

    @SerializedName("cnt")
    val cnt: Int = 0

    @SerializedName("cod")
    val cod: String? = null

    @SerializedName("message")
    val message: Double = 0.toDouble()

    @SerializedName("list")
    var list: List<ListItem>? = null

    override fun toString(): String {
        return "ForecastResponse{" +
                "city = '" + city + '\'' +
                ",cnt = '" + cnt + '\'' +
                ",cod = '" + cod + '\'' +
                ",message = '" + message + '\'' +
                ",list = '" + list + '\'' +
                "}"
    }
}