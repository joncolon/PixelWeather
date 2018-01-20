package com.tronography.pixelweather

import android.os.Handler
import android.os.Looper
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tronography.pixelweather.http.OpenWeatherApi
import com.tronography.pixelweather.model.CurrentWeatherResponse
import com.tronography.pixelweather.model.ForecastResponse
import com.tronography.pixelweather.model.ListItem
import io.reactivex.Single


fun Any.DEBUG(message: String) {
    Log.d(javaClass.simpleName, message)
}

fun Any.ERROR(message: String) {
    Log.e(javaClass.simpleName, message)
}