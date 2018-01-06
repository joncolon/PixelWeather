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
    private val FAHRENHEIT = "imperial"
    private var city: String? = null


    fun getWeatherReport(city: String): Single<WeatherReport> {
        setCity(city)
        val currentWeather = currentWeather
        val forecastReport = fiveDayForecast

        return Single.zip<CurrentWeatherModel, List<FiveDayForecastModel>, WeatherReport>(
                currentWeather,
                forecastReport,
                BiFunction(::WeatherReport))
                .subscribeOn(Schedulers.io());
    }

    private fun setCity(city: String) {
        this.city = city
    }

    private val currentWeather: Single<CurrentWeatherModel>?
        get() = city?.let {
            client.getCurrentWeather(it, FAHRENHEIT, OpenWeatherApi.apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { response: CurrentWeatherResponse ->
                        return@map buildCurrentWeatherModel(response)
                    }

        }


    private val fiveDayForecast: Single<List<FiveDayForecastModel>>?
        get() = city?.let {
            client.getForecast(it, FAHRENHEIT, OpenWeatherApi.apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
                    .flatMap { fromArray(it.list) }
                    .flatMapIterable({ it })
                    .map { listItem: ListItem ->
                        return@map buildForecastModel(listItem)
                    }
                    .toList()
                    .map { hourlyForecast: MutableList<HourlyForecastModel> ->
                        return@map buildFiveDayForecast(hourlyForecast)
                    }
        }


    fun buildCurrentWeatherModel(currentWeatherResponse: CurrentWeatherResponse): CurrentWeatherModel {
        val currentWeather = CurrentWeatherModel(
                currentWeatherResponse.name,
                currentWeatherResponse.sys.country,
                currentWeatherResponse.weather[0].description,
                currentWeatherResponse.weather[0].icon,
                currentWeatherResponse.clouds.all,
                currentWeatherResponse.main.humidity,
                currentWeatherResponse.main.pressure,
                currentWeatherResponse.main.tempMax,
                currentWeatherResponse.main.tempMin,
                currentWeatherResponse.main.temp,
                currentWeatherResponse.wind.speed,
                currentWeatherResponse.sys.sunrise.toLong(),
                currentWeatherResponse.sys.sunset.toLong(),
                currentWeatherResponse.dt.toLong())

        return currentWeather
    }

    private fun buildFiveDayForecast(hourlyForecast: MutableList<HourlyForecastModel>): List<FiveDayForecastModel> {
        val fiveDayMap: LinkedHashMap<String, FiveDayForecastModel> = LinkedHashMap()

        for (index in hourlyForecast.indices) {
            val day = hourlyForecast.get(index).dayOfWeek
            val forecast = hourlyForecast.get(index)


            if (fiveDayMap.get(day) == null) {
                val fiveDayModel = FiveDayForecastModel(
                        forecast.tempMax,
                        forecast.tempMin,
                        forecast.icon,
                        forecast.dayOfWeek
                )
                fiveDayModel.hourlyForecast.add(forecast)
                fiveDayMap.put(hourlyForecast.get(index).dayOfWeek, fiveDayModel)
            } else {
                val fiveDayModel: FiveDayForecastModel = fiveDayMap.get(day)!!
                fiveDayModel.hourlyForecast.add(forecast)

                if (fiveDayModel.tempMax < forecast.tempMax) {
                    fiveDayModel.tempMax = forecast.tempMax
                }

                if (fiveDayModel.tempMin > forecast.tempMin) {
                    fiveDayModel.tempMin = forecast.tempMin
                }

                fiveDayMap.put(day, fiveDayModel)
            }
        }

        return fiveDayMap.values.toList()
    }

    private fun buildForecastModel(listItem: ListItem): HourlyForecastModel {
        val forecast = HourlyForecastModel(
                listItem.main.tempMax,
                listItem.main.tempMin,
                listItem.weather[0].icon,
                listItem.dt.toLong()
        )

        return forecast
    }
}







