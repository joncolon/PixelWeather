package com.tronography.pixelweather.weather;

import com.tronography.pixelweather.model.WeatherReport;
import com.tronography.pixelweather.utils.SharedPrefsUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;


public class WeatherPresenter {

    private final SharedPrefsUtils sharedPrefsUtils;
    private final WeatherInteractor weatherInteractor;
    private Weather.View view;

    @Inject
    public WeatherPresenter(SharedPrefsUtils sharedPrefsUtils, WeatherInteractor weatherInteractor) {
        this.sharedPrefsUtils = sharedPrefsUtils;
        this.weatherInteractor = weatherInteractor;
    }

    void onQuerySubmitted(String city) {
        view.showLoading(true);
        sharedPrefsUtils.setLastCityQueried(city);
        showWeatherReport(city);
    }

    public void setView(Weather.View view) {
        this.view = view;
    }

    void release() {
        view = null;
    }

    void showWeatherFromLastQueriedCity() {
        String lastCityQueried = sharedPrefsUtils.getLastCityQueried();
        if (lastCityQueried != null) {
            showWeatherReport(lastCityQueried);
        }
    }

    public void showWeatherReport(String city){
        weatherInteractor.getWeatherReport(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<WeatherReport>() {

            @Override
            public void onSuccess(WeatherReport value) {
                view.showLoading(false);
                view.showWeatherReport(value);
            }

            @Override
            public void onError(Throwable e) {
                view.showLoading(false);
                view.showError(e.getMessage());
                System.out.println("e = " + e.getMessage());
            }
        });
    }
}

