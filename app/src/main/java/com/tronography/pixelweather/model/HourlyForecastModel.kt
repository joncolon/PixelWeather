package com.tronography.pixelweather.model

import com.tronography.pixelweather.utils.DateFormatter


class HourlyForecastModel internal constructor(
        val tempMax: Double,
        val tempMin: Double,
        val icon: String,
        val dateTime: Long
) {
    var dayOfWeek: String = DateFormatter.dayFormat(dateTime)

    override fun toString(): String {
        return "HourlyForecastModel(tempMax=$tempMax, tempMin=$tempMin, icon='$icon', dateTime=$dateTime, dayOfWeek='$dayOfWeek')"
    }
}
