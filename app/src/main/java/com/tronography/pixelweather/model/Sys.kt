package com.tronography.pixelweather.model

import com.google.gson.annotations.SerializedName

class Sys(
        @SerializedName("country") var country: String,
        @SerializedName("sunrise") var sunrise: Int,
        @SerializedName("sunset") var sunset: Int,
        @SerializedName("id") var id: Int,
        @SerializedName("type") var type: Int,
        @SerializedName("message") var message: Double
)