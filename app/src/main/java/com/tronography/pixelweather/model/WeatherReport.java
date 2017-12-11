package com.tronography.pixelweather.model;

import java.util.List;

/**
 * Created by jonat on 12/4/2017.
 */

public class WeatherReport {
    private CurrentWeatherModel currentWeather;
    private List<ForecastModel> forecast;

    public WeatherReport(CurrentWeatherModel currentWeather, List<ForecastModel> forecast) {
        this.currentWeather = currentWeather;
        this.forecast = forecast;
    }

    public WeatherReport() {
    }

    public CurrentWeatherModel getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeatherModel currentWeather) {
        this.currentWeather = currentWeather;
    }

    public List<ForecastModel> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastModel> forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "WeatherReport{" +
                "currentWeather=" + currentWeather +
                ", forecast=" + forecast +
                '}';
    }
}
