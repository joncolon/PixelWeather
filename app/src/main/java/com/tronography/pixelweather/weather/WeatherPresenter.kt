package com.tronography.pixelweather.weather

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
        view!!.showLoading(true)
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
        weatherInteractor.getWeatherReport(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableSingleObserver<WeatherReport>() {

                    override fun onSuccess(value: WeatherReport) {
                        view!!.showLoading(false)
                        view!!.showWeatherReport(value)
                    }

                    override fun onError(e: Throwable) {
                        view!!.showLoading(false)
                        view!!.showError(e.message)
                        println("e = " + e.message)
                    }
                })
    }
}

