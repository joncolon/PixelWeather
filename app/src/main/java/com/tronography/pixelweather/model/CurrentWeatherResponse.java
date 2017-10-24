package com.tronography.pixelweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherResponse {

    @SerializedName("dt")
    private int dt;

    @SerializedName("weather")
    private List<WeatherItem> weather;

    @SerializedName("name")
    private String name;

    @SerializedName("main")
    private Main main;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("id")
    private int id;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("wind")
    private Wind wind;

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getDt() {
        return dt;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Sys getSys() {
        return sys;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return "CurrentWeatherResponse{" +
                "dt=" + dt +
                ", weather=" + weather +
                ", name='" + name + '\'' +
                ", main=" + main +
                ", clouds=" + clouds +
                ", id=" + id +
                ", sys=" + sys +
                ", wind=" + wind +
                '}';
    }
}