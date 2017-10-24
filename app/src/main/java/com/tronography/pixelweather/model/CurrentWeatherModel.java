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

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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
