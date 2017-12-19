package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class WeatherItem {

    @SerializedName("icon")
    var icon: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("main")
    var main: String? = null

    @SerializedName("id")
    var id: Int = 0

    override fun toString(): String {
        return "WeatherItem{" +
                "icon = '" + icon + '\'' +
                ",description = '" + description + '\'' +
                ",main = '" + main + '\'' +
                ",id = '" + id + '\'' +
                "}"
    }
}