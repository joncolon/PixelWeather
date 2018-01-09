package com.tronography.pixelweather.weather

import android.util.Log
import com.tronography.pixelweather.http.OpenWeatherApi
import com.tronography.pixelweather.http.OpenWeatherClient
import com.tronography.pixelweather.model.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WeatherInteractor @Inject
constructor(private val client: OpenWeatherClient) {
    private val FAHRENHEIT = "imperial"
    private var city: String? = null


    fun queryWeather(city: String): Single<WeatherReport>? {
        setCity(city)
        return requestWeatherReport()
    }

    private fun setCity(city: String) {
        this.city = city
    }

    private fun requestWeatherReport(): Single<WeatherReport>? {
        Log.d("WeatherInteractor: ", "Requesting weather report...")
        return city?.let {
            client.getCurrentWeather(it, FAHRENHEIT, OpenWeatherApi.apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { response: CurrentWeatherResponse ->
                        return@map response.buildCurrentWeatherModel()
                    }
                    .flatMap { currentWeatherModel ->
                        generateWeatherReport(currentWeatherModel) }

        }
    }

    private fun generateWeatherReport(currentWeather: CurrentWeatherModel): Single<WeatherReport> {
        Log.d("WeatherInteractor: ", "Generating weather report...")
        return client.getForecast(city!!, FAHRENHEIT, OpenWeatherApi.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { forecastResponse -> Single.just(forecastResponse.list) }
                .map { listItems: List<ListItem> ->
                    return@map buildWeatherModel(listItems, currentWeather)
                }
    }


    private fun CurrentWeatherResponse.buildCurrentWeatherModel(): CurrentWeatherModel {
        val currentWeather = CurrentWeatherModel(
                city = name,
                country = sys.country,
                description = weather[0].description,
                icon = weather[0].icon,
                clouds = clouds.all,
                humidity = main.humidity,
                pressure = main.pressure,
                tempMax = main.tempMax,
                tempMin = main.tempMin,
                currentTemp = main.temp,
                windSpeed = wind.speed,
                sunrise = sys.sunrise.toLong(),
                sunset = sys.sunset.toLong(),
                dateTime = dt.toLong())

        return currentWeather
    }

    private fun buildWeatherModel(listItems: List<ListItem>, currentWeather: CurrentWeatherModel): WeatherReport {
        val fiveDayMap: LinkedHashMap<String, FiveDayForecastModel> = LinkedHashMap()
        val hourlyForecast: MutableList<HourlyForecastModel> = ArrayList()
        val fiveDayForecast = fiveDayMap.buildForecast(listItems, hourlyForecast)

        return WeatherReport(
                currentWeather,
                hourlyForecast,
                fiveDayForecast
        )
    }

    private fun LinkedHashMap<String, FiveDayForecastModel>.buildForecast(
            listItems: List<ListItem>,
            hourlyForecast: MutableList<HourlyForecastModel>): List<FiveDayForecastModel> {

        for (listItem in listItems) {
            val hourlyForecastModel = buildHourlyForecastModel(listItem)
            hourlyForecast.add(hourlyForecastModel)
            getFiveDayForecast(this, hourlyForecastModel)
        }

        val fiveDayForecast = values.toList()
        return fiveDayForecast
    }

    private fun buildHourlyForecastModel(listItem: ListItem): HourlyForecastModel {
        val hourlyModel = HourlyForecastModel(
                tempMax = listItem.main.tempMax,
                tempMin = listItem.main.tempMin,
                icon = listItem.weather[0].icon,
                dateTime = listItem.dt.toLong()
        )
        return hourlyModel
    }

    private fun getFiveDayForecast(fiveDayMap: LinkedHashMap<String, FiveDayForecastModel>, hourlyModel: HourlyForecastModel) {
        if (fiveDayMap[hourlyModel.dayOfWeek] == null) {
            fiveDayMap.addToFiveDayForecastMap(hourlyModel)
        } else {
            fiveDayMap.updateFiveDayForecastMaxAndMinTemp(hourlyModel)
        }
    }

    private fun LinkedHashMap<String, FiveDayForecastModel>.updateFiveDayForecastMaxAndMinTemp(
            hourlyModel: HourlyForecastModel
    ) {
        val fiveDayModel = get(hourlyModel.dayOfWeek)!!
        fiveDayModel.hourlyForecast.add(hourlyModel)

        if (fiveDayModel.tempMax < hourlyModel.tempMax) {
            fiveDayModel.tempMax = hourlyModel.tempMax
        }

        if (fiveDayModel.tempMin > hourlyModel.tempMin) {
            fiveDayModel.tempMin = hourlyModel.tempMin
        }

        put(hourlyModel.dayOfWeek, fiveDayModel)
    }

    private fun LinkedHashMap<String, FiveDayForecastModel>.addToFiveDayForecastMap(
            hourlyModel: HourlyForecastModel
    ) {
        val fiveDayModel = FiveDayForecastModel(
                tempMax = hourlyModel.tempMax,
                tempMin = hourlyModel.tempMin,
                icon = hourlyModel.icon,
                dayOfWeek = hourlyModel.dayOfWeek
        )
        fiveDayModel.hourlyForecast.add(hourlyModel)
        put(hourlyModel.dayOfWeek, fiveDayModel)
    }
}







