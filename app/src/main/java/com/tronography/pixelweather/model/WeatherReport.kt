package com.tronography.pixelweather.model

/**
 * Created by jonat on 12/4/2017.
 */

class WeatherReport {
    var currentWeather: CurrentWeatherModel? = null
    lateinit var fiveDayForecast: List<FiveDayForecastModel>

    constructor(currentWeather: CurrentWeatherModel, fiveDayForecast: List<FiveDayForecastModel>) {
        this.currentWeather = currentWeather
        this.fiveDayForecast = fiveDayForecast
    }

    constructor() {}
}
