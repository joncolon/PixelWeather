package com.tronography.pixelweather.utils


object IconUrlUtils {

    private val ICON_BASE_URL = "http://openweathermap.org/img/w/"
    private val PNG = ".png"

    fun getIconUrl(icon: String): String {
        return StringBuilder()
                .append(ICON_BASE_URL)
                .append(icon)
                .append(PNG)
                .toString()
    }

}
