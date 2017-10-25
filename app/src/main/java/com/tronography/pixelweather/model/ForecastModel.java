package com.tronography.pixelweather.model;


public class ForecastModel {
    private double tempMax;
    private double tempMin;
    private String icon;
    private long dateTime;

    ForecastModel(double tempMax, double tempMin, String icon, long dateTime) {
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.icon = icon;
        this.dateTime = dateTime;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public String getIcon() {
        return icon;
    }

    public long getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "ForecastModel{" +
                "tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", icon='" + icon + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
