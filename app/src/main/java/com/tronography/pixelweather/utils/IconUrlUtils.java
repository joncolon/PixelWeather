package com.tronography.pixelweather.utils;


public class IconUrlUtils {

    private static final String ICON_BASE_URL = "http://openweathermap.org/img/w/";
    private static final String PNG = ".png";

    public static String getIconUrl(String icon){
        return new StringBuilder()
                .append(ICON_BASE_URL)
                .append(icon)
                .append(PNG)
                .toString();
    }

}
