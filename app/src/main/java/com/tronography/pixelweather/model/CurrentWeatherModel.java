package com.tronography.pixelweather.model;


public class CurrentWeatherModel {

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

    CurrentWeatherModel(String city, String country, String description, String icon,
                        int clouds, int humidity, double pressure, double tempMax,
                        double tempMin, double currentTemp, double windSpeed, long sunrise,
                        long sunset, long dateTime) {
        this.city = city;
        this.country = country;
        this.description = description;
        this.icon = icon;
        this.clouds = clouds;
        this.humidity = humidity;
        this.pressure = pressure;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.currentTemp = currentTemp;
        this.windSpeed = windSpeed;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.dateTime = dateTime;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public int getClouds() {
        return clouds;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public long getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "CurrentWeatherModel{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", clouds=" + clouds +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", currentTemp=" + currentTemp +
                ", windSpeed=" + windSpeed +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", dateTime=" + dateTime +
                '}';
    }
}
