package com.tronography.pixelweather.model;

public class CurrentWeatherBuilder {
    private String city;
    private String country;
    private String description;
    private String icon;
    private int clouds;
    private int humidity;
    private double pressure;
    private double tempMax;
    private double tempMin;
    private double currentTemp;
    private double windSpeed;
    private long sunrise;
    private long sunset;
    private long dateTime;

    public CurrentWeatherBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public CurrentWeatherBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public CurrentWeatherBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CurrentWeatherBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public CurrentWeatherBuilder setClouds(int clouds) {
        this.clouds = clouds;
        return this;
    }

    public CurrentWeatherBuilder setHumidity(int humidity) {
        this.humidity = humidity;
        return this;
    }

    public CurrentWeatherBuilder setPressure(double pressure) {
        this.pressure = pressure;
        return this;
    }

    public CurrentWeatherBuilder setTempMax(double tempMax) {
        this.tempMax = tempMax;
        return this;
    }

    public CurrentWeatherBuilder setTempMin(double tempMin) {
        this.tempMin = tempMin;
        return this;
    }

    public CurrentWeatherBuilder setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
        return this;
    }

    public CurrentWeatherBuilder setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public CurrentWeatherBuilder setSunrise(long sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public CurrentWeatherBuilder setSunset(long sunset) {
        this.sunset = sunset;
        return this;
    }

    public CurrentWeatherBuilder setDateTime(long dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public CurrentWeatherModel build() {
        return new CurrentWeatherModel(city, country, description, icon, clouds, humidity, pressure, tempMax, tempMin, currentTemp, windSpeed, sunrise, sunset, dateTime);
    }
}