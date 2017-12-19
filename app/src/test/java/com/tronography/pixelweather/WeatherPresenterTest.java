package com.tronography.pixelweather;

import com.tronography.pixelweather.model.WeatherReport;
import com.tronography.pixelweather.utils.SharedPrefsUtils;
import com.tronography.pixelweather.weather.Weather;
import com.tronography.pixelweather.weather.WeatherInteractor;
import com.tronography.pixelweather.weather.WeatherPresenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {
    @ClassRule
    public static RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    WeatherInteractor weatherInteractor;
    @Mock
    Weather.View view;
    @Mock
    SharedPrefsUtils sharedPrefsUtils;

    private WeatherPresenter presenter;
    private WeatherReport weatherReport;

    @Before
    public void setup() {
        presenter = new WeatherPresenter(sharedPrefsUtils, weatherInteractor);
        presenter.setView(view);

        weatherReport = new WeatherReport();
    }

    @Test
    public void whenShowWeatherReportIsComplete_shouldShowResults() {
        when(weatherInteractor.getWeatherReport(anyString())).thenReturn(Single.just(weatherReport));

        presenter.showWeatherReport(anyString());

        verify(view).showWeatherReport(any(WeatherReport.class));
    }

    @Test
    public void whenErrorGettingWeatherReport_shouldShowError() {
        when(weatherInteractor.getWeatherReport(anyString())).thenReturn(Single.error(new Error("Error Message")));

        presenter.showWeatherReport(anyString());

        verify(view).showError(anyString());
    }
}
