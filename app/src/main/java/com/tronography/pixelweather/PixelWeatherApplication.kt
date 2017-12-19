package com.tronography.pixelweather

import android.app.Application

import com.tronography.pixelweather.dagger.AppComponent
import com.tronography.pixelweather.dagger.AppModule
import com.tronography.pixelweather.dagger.DaggerAppComponent
import com.tronography.pixelweather.dagger.NetworkModule
import com.tronography.pixelweather.dagger.SharedPrefModule


class PixelWeatherApplication : Application() {

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
    }

    fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .sharedPrefModule(SharedPrefModule())
                .build()
    }
}
