package com.tronography.pixelweather.model

import com.tronography.pixelweather.utils.DateFormatter


class CurrentWeatherModel constructor(
        val city: String,
        val country: String,
        val description: String,
        val icon: String,
        val clouds: Int,
        val humidity: Int,
        val pressure: Float,
        val tempMax: Float,
        val tempMin: Float,
        val currentTemp: Float,
        val windSpeed: Double,
        val sunrise: Long,
        val sunset: Long,
        val dateTime: Long
) {
    var dayOfWeek: String = DateFormatter.dayFormat(dateTime)
    override fun toString(): String {
        return "CurrentWeatherModel(" +
                "city='$city', " +
                "country='$country', " +
                "description='$description', " +
                "icon='$icon', clouds=$clouds, " +
                "humidity=$humidity, " +
                "pressure=$pressure, " +
                "tempMax=$tempMax, " +
                "tempMin=$tempMin, " +
                "currentTemp=$currentTemp, " +
                "windSpeed=$windSpeed, " +
                "sunrise=$sunrise, " +
                "sunset=$sunset, " +
                "dateTime=$dateTime, " +
                "dayOfWeek='$dayOfWeek')"
    }


}
