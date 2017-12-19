package com.tronography.pixelweather.model


class CurrentWeatherModel internal constructor(
        val city: String,
        val country: String,
        val description: String,
        val icon: String,
        val clouds: Int,
        val humidity: Int,
        val pressure: Double,
        val tempMax: Double,
        val tempMin: Double,
        val currentTemp: Double,
        val windSpeed: Double,
        val sunrise: Long,
        val sunset: Long,
        val dateTime: Long
)
