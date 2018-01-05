package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class ForecastResponse(
        @SerializedName("city") val city: City,
        @SerializedName("cod") val cod: String,
        @SerializedName("message") val message: String,
        @SerializedName("list") val list: List<ListItem>,
        @SerializedName("cnt") val cnt: Int
)