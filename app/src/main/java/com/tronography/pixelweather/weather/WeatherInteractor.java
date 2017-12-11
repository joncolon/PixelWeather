package com.tronography.pixelweather.weather;

import android.support.annotation.NonNull;

import com.tronography.pixelweather.http.OpenWeatherClient;
import com.tronography.pixelweather.model.CurrentWeatherBuilder;
import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.CurrentWeatherResponse;
import com.tronography.pixelweather.model.ForecastBuilder;
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.model.ForecastResponse;
import com.tronography.pixelweather.model.ListItem;
import com.tronography.pixelweather.model.WeatherReport;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.tronography.pixelweather.http.OpenWeatherApi.getApiKey;
import static io.reactivex.Observable.fromArray;


public class WeatherInteractor {

    private OpenWeatherClient client;
    private static final String FAHRENHEIT = "imperial";
    private String city;
    private WeatherReport weatherReport;

    @Inject
    public WeatherInteractor(OpenWeatherClient client) {
        this.client = client;
    }


    public Single<WeatherReport> getWeatherReport(String city){
        setCity(city);
        Single<CurrentWeatherModel> currentWeather = getCurrentWeather();
        Single<List<ForecastModel>> forecastReport = getForecastReport();

        return Single.zip(currentWeather, forecastReport,
                (currentWeather1, forecast) -> {
                    weatherReport = new WeatherReport(currentWeather1, forecast);
                    return weatherReport;
                }).subscribeOn(Schedulers.io());
    }

    private void setCity(String city) {
        this.city = city;
    }

    Single<CurrentWeatherModel> getCurrentWeather() {
        return client.getCurrentWeather(city, FAHRENHEIT, getApiKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::buildCurrentWeatherModel);
    }

    @NonNull
    private CurrentWeatherModel buildCurrentWeatherModel(CurrentWeatherResponse currentWeatherResponse) {
        CurrentWeatherBuilder currentWeatherBuilder = new CurrentWeatherBuilder();

        CurrentWeatherModel currentWeather = currentWeatherBuilder
                .setCity(currentWeatherResponse.getName())
                .setClouds(currentWeatherResponse.getClouds().getAll())
                .setTempMax(currentWeatherResponse.getMain().getTempMax())
                .setTempMin(currentWeatherResponse.getMain().getTempMin())
                .setDescription(currentWeatherResponse.getWeather().get(0).getDescription())
                .setIcon(currentWeatherResponse.getWeather().get(0).getIcon())
                .setHumidity(currentWeatherResponse.getMain().getHumidity())
                .setSunrise(currentWeatherResponse.getSys().getSunrise())
                .setSunset(currentWeatherResponse.getSys().getSunset())
                .setCountry(currentWeatherResponse.getSys().getCountry())
                .setWindSpeed(currentWeatherResponse.getWind().getSpeed())
                .setPressure(currentWeatherResponse.getMain().getPressure())
                .setDateTime(currentWeatherResponse.getDt())
                .setCurrentTemp(currentWeatherResponse.getMain().getTemp())
                .build();

        return currentWeather;
    }
    private Single<List<ForecastModel>> getForecastReport() {
        Single<ForecastResponse> request = client.getForecast(city, FAHRENHEIT, getApiKey());

        return request
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .flatMap(forecastResponse -> fromArray(forecastResponse.getList()))
                .flatMapIterable(list -> list)
                .map(this::buildForecastModel)
                .toList();
    }

    private ForecastModel buildForecastModel(ListItem listItem) {
        ForecastBuilder forecastBuilder = new ForecastBuilder();

        return forecastBuilder
                .setDateTime(listItem.getDt())
                .setIcon(listItem.getWeather().get(0).getIcon())
                .setTempMax(listItem.getMain().getTempMax())
                .setTempMin(listItem.getMain().getTempMin())
                .build();
    }
}
