package com.tronography.pixelweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {

    @SerializedName("city")
    private City city;

    @SerializedName("cnt")
    private int cnt;

    @SerializedName("cod")
    private String cod;

    @SerializedName("message")
    private double message;

    @SerializedName("list")
    private List<ListItem> list;

    public City getCity() {
        return city;
    }

    public int getCnt() {
        return cnt;
    }

    public String getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public List<ListItem> getList() {
        return list;
    }

    @Override
    public String toString() {
        return
                "ForecastResponse{" +
                        "city = '" + city + '\'' +
                        ",cnt = '" + cnt + '\'' +
                        ",cod = '" + cod + '\'' +
                        ",message = '" + message + '\'' +
                        ",list = '" + list + '\'' +
                        "}";
    }
}