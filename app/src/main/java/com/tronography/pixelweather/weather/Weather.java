package com.tronography.pixelweather.weather;

import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.ForecastModel;

import java.util.List;


public interface Weather {

    interface View {

        void showLoading(boolean show);

        void showWeatherReport(CurrentWeatherModel currentWeatherModel, List<ForecastModel> results);

        void showError(String error);
    }
}