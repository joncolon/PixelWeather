package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class City {

    @SerializedName("country")
    var country: String? = null

    @SerializedName("coord")
    var coord: Coord? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("id")
    var id: Int = 0

    override fun toString(): String {
        return "City{" +
                "country = '" + country + '\'' +
                ",coord = '" + coord + '\'' +
                ",name = '" + name + '\'' +
                ",id = '" + id + '\'' +
                "}"
    }
}