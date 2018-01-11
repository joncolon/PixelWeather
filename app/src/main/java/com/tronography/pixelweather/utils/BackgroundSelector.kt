package com.tronography.pixelweather.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import com.tronography.pixelweather.R
import com.tronography.pixelweather.model.CurrentWeatherModel


internal object BackgroundSelector {

    fun getPercentageOfSunshineLeft(currentWeatherModel: CurrentWeatherModel): Int {
        val now = System.currentTimeMillis()
        val start = currentWeatherModel.sunrise * 1000
        val end = currentWeatherModel.sunset * 1000

        if (start >= end || now >= end) {
            return 0
        }
        if (now <= start) {
            return 100
        }
        val sunshineLeft = ((end - now) * 100 / (end - start)).toInt()

        println("sunshineLeft = $sunshineLeft%")
        return sunshineLeft
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun getSkyGradientBackgroundResourceId (context : Context, currentWeatherModel: CurrentWeatherModel): Drawable {

        val remainingSunshine = getPercentageOfSunshineLeft(currentWeatherModel)
        Log.d("BackgroundSelector", "Sunshine percentage = $remainingSunshine")

        val isSunrise = remainingSunshine <= 99 && remainingSunshine > 95
        val isEarlyMorning = remainingSunshine <= 95 && remainingSunshine > 90
        val isDaytime = remainingSunshine <= 90 && remainingSunshine > 45
        val isEvening = remainingSunshine <= 45 && remainingSunshine > 5
        val isSunset = remainingSunshine <= 5 && remainingSunshine > 0

        when {
            isSunrise -> return context.getDrawable(R.drawable.gradient_sky_dawn)
            isEarlyMorning -> return context.getDrawable(R.drawable.gradient_sky_early_morn)
            isDaytime -> return context.getDrawable(R.drawable.gradient_sky_noon_blue)
            isEvening -> return context.getDrawable(R.drawable.gradient_sky_evening)
            isSunset -> return context.getDrawable(R.drawable.gradient_sky_sunset)
            else -> return context.getDrawable(R.drawable.gradient_sky_night)
        }
    }
}

