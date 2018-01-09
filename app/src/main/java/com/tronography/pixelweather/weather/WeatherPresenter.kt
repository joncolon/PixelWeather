package com.tronography.pixelweather.weather

import android.util.Log
import com.tronography.pixelweather.model.WeatherReport
import com.tronography.pixelweather.utils.SharedPrefsUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject


class WeatherPresenter @Inject
constructor(
        private val sharedPrefsUtils: SharedPrefsUtils,
        private val weatherInteractor: WeatherInteractor
) {
    private var view: Weather.View? = null

    fun onQuerySubmitted(city: String) {
        view?.showLoading(true)
        sharedPrefsUtils.lastCityQueried = city
        showWeatherReport(city)
    }

    fun setView(view: Weather.View) {
        this.view = view
    }

    fun release() {
        view = null
    }

    fun showWeatherFromLastQueriedCity() {
        val lastCityQueried = sharedPrefsUtils.lastCityQueried
        if (lastCityQueried != null) {
            showWeatherReport(lastCityQueried)
        }
    }

    fun showWeatherReport(city: String) {
        weatherInteractor.queryWeather(city)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : DisposableSingleObserver<WeatherReport>() {

                    override fun onSuccess(weatherReport: WeatherReport) {
                        view?.showLoading(false)
                        view?.showWeatherReport(weatherReport)


                        println("deserializedWeather = ${sharedPrefsUtils.cachedWeatherReport?.let { WeatherReport.create(it) }}")
                        println("weatherJson = ${weatherReport.serialize()}")
                        sharedPrefsUtils.cachedWeatherReport = weatherReport.serialize()

                    }

                    override fun onError(e: Throwable) {
                        view?.showLoading(false)
                        view?.showError(e.message)
                        Log.e("ERROR: ", "e = " + e.stackTrace.toString())
                    }
                })
    }

    fun showCachedWeatherReport() {
        val cachedWeatherReport = sharedPrefsUtils.cachedWeatherReport?.let { WeatherReport.create(it) }
        cachedWeatherReport?.let { view?.showWeatherReport(it) }
    }
}

