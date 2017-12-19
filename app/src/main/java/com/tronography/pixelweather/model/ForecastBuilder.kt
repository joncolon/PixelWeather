package com.tronography.pixelweather.model

class ForecastBuilder {
    private var tempMax: Double = 0.toDouble()
    private var tempMin: Double = 0.toDouble()
    private var icon: String? = null
    private var dateTime: Long = 0

    fun setTempMax(tempMax: Double): ForecastBuilder {
        this.tempMax = tempMax
        return this
    }

    fun setTempMin(tempMin: Double): ForecastBuilder {
        this.tempMin = tempMin
        return this
    }

    fun setIcon(icon: String): ForecastBuilder {
        this.icon = icon
        return this
    }

    fun setDateTime(dateTime: Long): ForecastBuilder {
        this.dateTime = dateTime
        return this
    }

    fun build(): ForecastModel {
        return ForecastModel(tempMax, tempMin, icon!!, dateTime)
    }
}