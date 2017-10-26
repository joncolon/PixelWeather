package com.tronography.pixelweather.weather;

import android.support.annotation.NonNull;

import com.tronography.pixelweather.http.OpenWeatherApi;
import com.tronography.pixelweather.http.OpenWeatherClient;
import com.tronography.pixelweather.model.CurrentWeatherBuilder;
import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.CurrentWeatherResponse;
import com.tronography.pixelweather.model.ForecastBuilder;
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.model.ListItem;
import com.tronography.pixelweather.utils.SharedPrefsUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


import static io.reactivex.Observable.fromArray;


public class WeatherPresenter {

    private static final String FAHRENHEIT = "imperial";
    private static final String UNITED_STATES = ",US";
    private final SharedPrefsUtils sharedPrefsUtils;
    private OpenWeatherClient client;
    private CompositeDisposable disposables = new CompositeDisposable();
    private CurrentWeatherModel currentWeatherModel;
    private Weather.View view;

    @Inject
    public WeatherPresenter(OpenWeatherClient client, SharedPrefsUtils sharedPrefsUtils) {
        this.client = client;
        this.sharedPrefsUtils = sharedPrefsUtils;
    }

    void onQuerySubmitted(String query) {
        view.showLoading(true);
        getCurrentWeather(query);
    }

    public void setView(Weather.View view) {
        this.view = view;
    }

    void release() {
        view = null;
        disposables.clear();
    }

    void checkLastQueriedCity() {
        String lastCityQueried = sharedPrefsUtils.getLastCityQueried();
        if (lastCityQueried != null) {
            getCurrentWeather(lastCityQueried);
        }
    }

    //made public to be visible for testing
    public void getForecast(String city) {
        String usaCity = city + UNITED_STATES;
        client.getForecast(usaCity, FAHRENHEIT, OpenWeatherApi.getApiKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .flatMap(forecastResponse -> fromArray(forecastResponse.getList()))
                .flatMapIterable(list -> list)
                .map(this::mapToForecastModel)
                .toList()
                .subscribe(this::onGetForecastSuccess, e -> onGetForecastFailure(e.getMessage()));
    }

    private ForecastModel mapToForecastModel(ListItem listItem) {
        ForecastBuilder forecastBuilder = new ForecastBuilder();
        ForecastModel forecast = forecastBuilder
                .setDateTime(listItem.getDt())
                .setIcon(listItem.getWeather().get(0).getIcon())
                .setTempMax(listItem.getMain().getTempMax())
                .setTempMin(listItem.getMain().getTempMin())
                .createForecastModel();

        return forecast;
    }

    //made public to be visible for testing
    public void getCurrentWeather(String city) {
        String usaCity = city + UNITED_STATES;
        sharedPrefsUtils.setLastCityQueried(usaCity);

        Single<CurrentWeatherResponse> request = client.getCurrentWeather(usaCity, FAHRENHEIT,
                OpenWeatherApi.getApiKey());

        disposables.add(request
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::mapToCurrentWeatherModel)
                .subscribe(this::onGetCurrentSuccess,
                        e -> onGetCurrentFailure(e.getMessage())));
    }

    private void onGetCurrentSuccess(CurrentWeatherModel currentWeatherModelResult) {
        getForecast(currentWeatherModelResult.getCity());
        currentWeatherModel = currentWeatherModelResult;
    }

    private void onGetCurrentFailure(String error) {
        view.showLoading(false);
        view.showError(error);
    }

    private void onGetForecastSuccess(List<ForecastModel> forecast) {
        view.showLoading(false);
        view.showWeatherReport(currentWeatherModel, forecast);
    }

    private void onGetForecastFailure(String error) {
        view.showError(error);
    }

    @NonNull
    private CurrentWeatherModel mapToCurrentWeatherModel(CurrentWeatherResponse currentWeatherResponse) {

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
                .createCurrentWeatherModel();

        return currentWeather;
    }
}

