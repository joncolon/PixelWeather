package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class City(
        @SerializedName("country") var country: String,
        @SerializedName("coord") var coord: Coord,
        @SerializedName("name") var name: String,
        @SerializedName("id") var id: Int
)