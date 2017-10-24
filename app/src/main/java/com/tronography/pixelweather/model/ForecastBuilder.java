package com.tronography.pixelweather.model;

public class ForecastBuilder {
    private double tempMax;
    private double tempMin;
    private String icon;
    private long dateTime;

    public ForecastBuilder setTempMax(double tempMax) {
        this.tempMax = tempMax;
        return this;
    }

    public ForecastBuilder setTempMin(double tempMin) {
        this.tempMin = tempMin;
        return this;
    }

    public ForecastBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public ForecastBuilder setDateTime(long dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public ForecastModel createForecastModel() {
        return new ForecastModel(tempMax, tempMin, icon, dateTime);
    }
}