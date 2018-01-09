package com.tronography.pixelweather.model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

/**
 * Created by jonat on 12/4/2017.
 */

data class WeatherReport(
        var currentWeather: CurrentWeatherModel,
        var hourlyForecast: List<HourlyForecastModel>,
        var fiveDayForecast: List<FiveDayForecastModel>

) {
    override fun toString(): String {
        return "WeatherReport(currentWeather=$currentWeather, hourlyForecast=$hourlyForecast, fiveDayForecast=$fiveDayForecast)"
    }

    @Synchronized
    fun serialize(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json: String = gson.toJson(this)
        return json;
    }

    companion object {
        @Synchronized
        fun create(serializedData: String): WeatherReport {
            val gson = Gson()
            val weatherReport: WeatherReport = gson.fromJson<WeatherReport>(serializedData, WeatherReport::class.java)
            print("Deserialized Weather Report :" + weatherReport.toString())
            return weatherReport
        }
    }
}
