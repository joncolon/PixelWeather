package com.tronography.pixelweather.http;

import com.tronography.pixelweather.model.CurrentWeatherResponse;
import com.tronography.pixelweather.model.ForecastResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenWeatherClient {

    @GET("data/2.5/weather")
    Single<CurrentWeatherResponse> getCurrentWeather(
            @Query("q") String query,
            @Query("units") String unit,
            @Query("APPID") String apiKey);

    @GET("data/2.5/forecast")
    Single<ForecastResponse> getForecast(
            @Query("q") String query,
            @Query("units") String unit,
            @Query("APPID") String apiKey);
}

