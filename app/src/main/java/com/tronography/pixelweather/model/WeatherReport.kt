package com.tronography.pixelweather.model

/**
 * Created by jonat on 12/4/2017.
 */

class WeatherReport {
    var currentWeather: CurrentWeatherModel? = null
    var forecast: List<ForecastModel>? = null

    constructor(currentWeather: CurrentWeatherModel, forecast: List<ForecastModel>) {
        this.currentWeather = currentWeather
        this.forecast = forecast
    }

    constructor() {}
}
