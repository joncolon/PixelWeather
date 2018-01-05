package com.tronography.pixelweather.model

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jonat on 1/3/2018.
 */

class FiveDayForecastModel(
        var tempMax: Double,
        var tempMin: Double,
        var icon: String,
        var dayOfWeek: String
) {
    var hourlyForecast: MutableList<HourlyForecastModel> = ArrayList<HourlyForecastModel>()

    override fun toString(): String {
        return "FiveDayForecastModel(tempMax=$tempMax, tempMin=$tempMin, icon=$icon, dayOfWeek=$dayOfWeek, hourlyForecast=$hourlyForecast)"
    }
}
