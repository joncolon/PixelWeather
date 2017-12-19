package com.tronography.pixelweather.model


class ForecastModel internal constructor(
        val tempMax: Double,
        val tempMin: Double,
        val icon: String,
        val dateTime: Long
) {

}
