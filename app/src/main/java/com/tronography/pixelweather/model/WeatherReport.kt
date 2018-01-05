package com.tronography.pixelweather.model

/**
 * Created by jonat on 12/4/2017.
 */

class WeatherReport {
    var currentWeather: CurrentWeatherModel? = null
    var hourlyForecast: List<FiveDayForecastModel>? = null

    constructor(currentWeather: CurrentWeatherModel, hourlyForecast: List<FiveDayForecastModel>) {
        this.currentWeather = currentWeather
        this.hourlyForecast = hourlyForecast
    }

    constructor() {}
}
