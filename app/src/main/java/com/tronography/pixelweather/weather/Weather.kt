package com.tronography.pixelweather.weather

import com.tronography.pixelweather.model.WeatherReport


interface Weather {

    interface View {

        fun showLoading(show: Boolean)

        fun showError(error: String?)

        fun showWeatherReport(weatherReport: WeatherReport)
    }
}