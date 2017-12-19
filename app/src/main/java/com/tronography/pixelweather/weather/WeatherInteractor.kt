package com.tronography.pixelweather.weather

import com.tronography.pixelweather.http.OpenWeatherApi
import com.tronography.pixelweather.http.OpenWeatherClient
import com.tronography.pixelweather.model.*
import io.reactivex.Observable.fromArray
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WeatherInteractor @Inject
constructor(private val client: OpenWeatherClient) {
    private var city: String? = null

    companion object {
        private val FAHRENHEIT = "imperial"
    }

    fun getWeatherReport(city: String): Single<WeatherReport> {
        setCity(city)
        val currentWeather = currentWeather
        val forecastReport = forecastReport

        return Single.zip<CurrentWeatherModel, List<ForecastModel>, WeatherReport>(
                currentWeather,
                forecastReport,
                BiFunction(::WeatherReport))
                .subscribeOn(Schedulers.io());
    }

    private fun setCity(city: String) {
        this.city = city
    }

    private val currentWeather: Single<CurrentWeatherModel>
        get() = client.getCurrentWeather(city!!, FAHRENHEIT, OpenWeatherApi.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { response: CurrentWeatherResponse? ->
                    return@map buildCurrentWeatherModel(response)
                }

    private val forecastReport: Single<List<ForecastModel>>
        get() = client.getForecast(city!!, FAHRENHEIT, OpenWeatherApi.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .flatMap { forecastResponse -> fromArray(forecastResponse.list!!) }
                .flatMapIterable { list -> list }
                .map { response: ListItem? ->
                    return@map buildForecastModel(response)
                }
                .toList()



    private fun buildCurrentWeatherModel(currentWeatherResponse: CurrentWeatherResponse?): CurrentWeatherModel {
        val currentWeatherBuilder = CurrentWeatherBuilder()

        return currentWeatherBuilder
                .setCity(currentWeatherResponse!!.name!!)
                .setClouds(currentWeatherResponse.clouds!!.all)
                .setTempMax(currentWeatherResponse.main!!.tempMax)
                .setTempMin(currentWeatherResponse.main!!.tempMin)
                .setDescription(currentWeatherResponse.weather!![0].description!!)
                .setIcon(currentWeatherResponse.weather!![0].icon!!)
                .setHumidity(currentWeatherResponse.main!!.humidity)
                .setSunrise(currentWeatherResponse.sys!!.sunrise.toLong())
                .setSunset(currentWeatherResponse.sys!!.sunset.toLong())
                .setCountry(currentWeatherResponse.sys!!.country!!)
                .setWindSpeed(currentWeatherResponse.wind!!.speed)
                .setPressure(currentWeatherResponse.main!!.pressure)
                .setDateTime(currentWeatherResponse.dt.toLong())
                .setCurrentTemp(currentWeatherResponse.main!!.temp)
                .build()
    }

    private fun buildForecastModel(listItem: ListItem?): ForecastModel {
        val forecastBuilder = ForecastBuilder()

        return forecastBuilder
                .setDateTime(listItem!!.dt.toLong())
                .setIcon(listItem.weather!![0].icon!!)
                .setTempMax(listItem.main!!.tempMax)
                .setTempMin(listItem.main!!.tempMin)
                .build()
    }
}



