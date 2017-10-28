package com.tronography.pixelweather;

import com.tronography.pixelweather.http.OpenWeatherClient;
import com.tronography.pixelweather.model.Clouds;
import com.tronography.pixelweather.model.CurrentWeatherBuilder;
import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.CurrentWeatherResponse;
import com.tronography.pixelweather.model.ForecastResponse;
import com.tronography.pixelweather.model.Main;
import com.tronography.pixelweather.model.Sys;
import com.tronography.pixelweather.model.WeatherItem;
import com.tronography.pixelweather.model.Wind;
import com.tronography.pixelweather.utils.SharedPrefsUtils;
import com.tronography.pixelweather.weather.Weather;
import com.tronography.pixelweather.weather.WeatherPresenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {
    @ClassRule
    public static RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    OpenWeatherClient client;
    @Mock
    Weather.View view;
    @Mock
    SharedPrefsUtils sharedPrefsUtils;

    private WeatherPresenter presenter;
    private ForecastResponse forecastResponse;
    private List<ForecastResponse> searchResults;
    private CurrentWeatherResponse currentWeatherResponse;
    private CurrentWeatherModel currentWeatherModel;

    @Before
    public void setup() {
        presenter = new WeatherPresenter(client, sharedPrefsUtils);
        presenter.setView(view);

        searchResults = new ArrayList<>();

        forecastResponse = new ForecastResponse();
        forecastResponse.setList(new ArrayList<>());

        currentWeatherResponse = new CurrentWeatherResponse();
        currentWeatherResponse.setName("new york");
        currentWeatherResponse.setClouds(new Clouds());
        currentWeatherResponse.getClouds().setAll(20);
        currentWeatherResponse.setMain(new Main());
        currentWeatherResponse.getMain().setTempMin(1);
        currentWeatherResponse.getMain().setTempMax(2);
        currentWeatherResponse.getMain().setTemp(2);
        currentWeatherResponse.getMain().setHumidity(20);
        currentWeatherResponse.getMain().setPressure(20);
        currentWeatherResponse.setSys(new Sys());
        currentWeatherResponse.getSys().setCountry("US");
        currentWeatherResponse.getSys().setSunrise(10000);
        currentWeatherResponse.getSys().setSunset(20000);
        currentWeatherResponse.setDt(1508103000);
        currentWeatherResponse.setWeather(new ArrayList<>());
        currentWeatherResponse.getWeather().add(0, new WeatherItem());
        currentWeatherResponse.getWeather().get(0).setIcon("01n");
        currentWeatherResponse.getWeather().get(0).setMain("main");
        currentWeatherResponse.getWeather().get(0).setDescription("description");
        currentWeatherResponse.setWind(new Wind());
        currentWeatherResponse.getWind().setSpeed(11);

        CurrentWeatherBuilder currentWeatherBuilder = new CurrentWeatherBuilder();
        currentWeatherModel = currentWeatherBuilder
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
    }

    @Test
    public void whenGetForecastIsComplete_shouldShowResults() {
        searchResults.add(forecastResponse);

        when(client.getForecast(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(forecastResponse));

        presenter.getForecast(currentWeatherModel);

        verify(view).showWeatherReport(any(CurrentWeatherModel.class), anyList());
    }

    @Test
    public void whenGetCurrentIsComplete_shouldCallGetForecast() {
        when(client.getCurrentWeather(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(currentWeatherResponse));

        presenter.getWeatherReport("new york");
    }
}
