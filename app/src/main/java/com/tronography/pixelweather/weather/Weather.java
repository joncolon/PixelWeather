package com.tronography.pixelweather.weather;

import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.model.WeatherReport;

import java.util.List;


public interface Weather {

    interface View {

        void showLoading(boolean show);

        void showError(String error);

        void showToast(String message);

        void showWeatherReport(WeatherReport weatherReport);
    }
}