package com.tronography.pixelweather.weather

import android.content.ContentValues.TAG
import android.util.Log
import com.tronography.pixelweather.common.Presenter
import com.tronography.pixelweather.model.WeatherReport
import com.tronography.pixelweather.utils.SharedPrefsUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class WeatherPresenter @Inject
constructor(
        private val sharedPrefsUtils: SharedPrefsUtils,
        private val weatherInteractor: WeatherInteractor
) : Presenter<Weather.View> {

    private var count = 0
    private var view: Weather.View? = null


    override fun onViewAttached(view: Weather.View) {
        count++
        this.view = view
        Log.d("WeatherPresenter : ", "Presenter( $this ) attached ${count} times")
    }

    override fun onViewDetached() {
        release()
    }

    override fun onDestroyed() {
        //nothing to dispose
    }

    fun onQuerySubmitted(city: String) {
        view?.showLoading(true)
        weatherInteractor.clearWeatherReport()
        sharedPrefsUtils.lastCityQueried = city
        showWeatherReport(city)
        Log.d("WeatherPresenter", "city : ${city}")
    }

    fun release() {
        Log.d("WeatherPresenter", "releasing...")
        this.view = null
        weatherReportObservable = null
    }

    fun showWeatherFromLastQueriedCity() {
        val lastCityQueried = sharedPrefsUtils.lastCityQueried

        when {
            lastCityQueried != null -> {
                Log.d("WeatherPresenter : ", "lastCityQueried = ${lastCityQueried}")
                showWeatherReport(lastCityQueried)
            }
        }

        Log.d("WeatherPresenter : ", "lastCityQueried = ${lastCityQueried}")
    }

    var weatherReportObservable: Observable<WeatherReport>? = null

    fun showWeatherReport(city: String) {
        Log.d("WeatherPresenter : ", "showWeatherReport")

        weatherReportObservable = weatherInteractor.queryWeather(city)
        weatherReportObservable
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<WeatherReport> {

                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                        Log.d("WeatherPresenter : ", "retrieving weather report...")
                    }

                    override fun onNext(weatherReport: WeatherReport?) {
                        Log.d("WeatherPresenter : ", "OnNext...")
                        Log.d("WeatherPresenter : ", "weatherReport = ${weatherReport}")

                        view?.showLoading(false)
                        Log.d("WeatherPresenter : ", "Serializing Weather Report...")
                        weatherReport?.let { view?.showWeatherReport(it) }
                        sharedPrefsUtils.cachedWeatherReport = weatherReport?.serialize()
                    }

                    override fun onError(e: Throwable?) {
                        view?.showLoading(false)
                        view?.showError(e?.message)
                        Log.e("WeatherPresenter : ", "onError = " + e.toString())
                    }
                })
    }

    fun replayWeatherReport() {
    }

    fun showCachedWeatherReport() {
        Log.d(TAG, "Loading cached Weather Report...")
        val cachedWeatherReport = sharedPrefsUtils.cachedWeatherReport?.let { WeatherReport.create(it) }
        cachedWeatherReport?.let { view?.showWeatherReport(it) }
    }
}

