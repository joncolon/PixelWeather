package com.tronography.pixelweather.model

/**
 * Created by jonat on 1/3/2018.
 */

class FiveDayForecastModel(
        var tempMax: Double,
        var tempMin: Double,
        var icon: String,
        var dayOfWeek: String
) {
    lateinit var hourlyForecast: MutableList<HourlyForecastModel>

    lateinit var twelveHourForecast: MutableList<HourlyForecastModel>

    override fun toString(): String {
        return "FiveDayForecastModel(tempMax=$tempMax, tempMin=$tempMin, icon=$icon, dayOfWeek=$dayOfWeek, fiveDayForecast=$hourlyForecast)"
    }
}
