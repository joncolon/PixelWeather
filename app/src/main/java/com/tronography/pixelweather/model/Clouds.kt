package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Clouds {

    @SerializedName("all")
    var all: Int = 0

    override fun toString(): String {
        return "Clouds{" +
                "all = '" + all + '\'' +
                "}"
    }
}