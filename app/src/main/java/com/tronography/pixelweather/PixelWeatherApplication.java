package com.tronography.pixelweather;

import android.app.Application;

import com.tronography.pixelweather.dagger.AppComponent;
import com.tronography.pixelweather.dagger.AppModule;
import com.tronography.pixelweather.dagger.DaggerAppComponent;
import com.tronography.pixelweather.dagger.NetworkModule;
import com.tronography.pixelweather.dagger.SharedPrefModule;


public class PixelWeatherApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = createComponent();
    }

    public AppComponent getAppComponent() {
        return component;
    }

    public AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .sharedPrefModule(new SharedPrefModule())
                .build();
    }
}
