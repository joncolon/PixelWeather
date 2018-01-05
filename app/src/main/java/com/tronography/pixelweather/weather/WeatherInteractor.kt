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
        val forecastReport = hourlyForecastReport

        return Single.zip<CurrentWeatherModel, List<FiveDayForecastModel>, WeatherReport>(
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
                .map { response: CurrentWeatherResponse ->
                    return@map buildCurrentWeatherModel(response)
                }

    private val hourlyForecastReport: Single<List<FiveDayForecastModel>>?
        get() = client.getForecast(city!!, FAHRENHEIT, OpenWeatherApi.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .flatMap { forecastResponse -> fromArray(forecastResponse.list) }
                .flatMapIterable { list -> list }
                .map { response: ListItem ->
                    return@map buildForecastModel(response)
                }
                .toList()
                .map {list:MutableList<HourlyForecastModel> ->
                    return@map buildFiveDayForecast(list) }


    private fun buildCurrentWeatherModel(currentWeatherResponse: CurrentWeatherResponse): CurrentWeatherModel {
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

    fun buildFiveDayForecast(hourlyForecastModel: MutableList<HourlyForecastModel>): List<FiveDayForecastModel> {
        val fiveDayMap: LinkedHashMap<String, FiveDayForecastModel> = LinkedHashMap()

        for (i in hourlyForecastModel.indices) {
            val day = hourlyForecastModel.get(i).dayOfWeek

            println(day)
            val forecast = hourlyForecastModel.get(i)

            if (!day.equals("TODAY")) {
                if (fiveDayMap.get(day) == null) {
                    val fiveDayModel = FiveDayForecastModel(
                            forecast.tempMax,
                            forecast.tempMin,
                            forecast.icon,
                            forecast.dayOfWeek
                    )
                    fiveDayModel.hourlyForecast.add(forecast)
                    fiveDayMap.put(hourlyForecastModel.get(i).dayOfWeek, fiveDayModel)
                } else {
                    val fiveDayModel = fiveDayMap.get(day);
                    fiveDayModel?.hourlyForecast?.add(forecast)

                    if (fiveDayModel?.tempMax!! < forecast.tempMax) {
                        fiveDayModel.tempMax = forecast.tempMax
                    }

                    if (fiveDayModel.tempMin!! > forecast.tempMin) {
                        fiveDayModel.tempMin = forecast.tempMin
                    }

                    fiveDayMap.put(day, fiveDayModel)
                }
            }
        }

        val fiveDayList = fiveDayMap.values.toList()

        for (forecast in fiveDayList) {
            println(forecast.toString())
        }
        return fiveDayList
    }
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






