package com.tronography.pixelweather.dagger;

import com.tronography.pixelweather.weather.WeatherActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, SharedPrefModule.class})
public interface AppComponent {

    void inject(WeatherActivity target);
}
