package com.tronography.pixelweather.weather

import com.tronography.pixelweather.common.PresenterFactory
import com.tronography.pixelweather.utils.SharedPrefsUtils
import javax.inject.Inject

class WeatherPresenterFactory @Inject constructor(
        private val sharedPrefsUtils: SharedPrefsUtils,
        private val weatherInteractor: WeatherInteractor) : PresenterFactory<WeatherPresenter> {

    override fun create(): WeatherPresenter {
        return WeatherPresenter(sharedPrefsUtils, weatherInteractor)
    }
}