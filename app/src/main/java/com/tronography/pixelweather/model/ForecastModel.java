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

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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
