package com.tronography.pixelweather.model

class CurrentWeatherBuilder {
    private var city: String? = null
    private var country: String? = null
    private var description: String? = null
    private var icon: String? = null
    private var clouds: Int = 0
    private var humidity: Int = 0
    private var pressure: Double = 0.toDouble()
    private var tempMax: Double = 0.toDouble()
    private var tempMin: Double = 0.toDouble()
    private var currentTemp: Double = 0.toDouble()
    private var windSpeed: Double = 0.toDouble()
    private var sunrise: Long = 0
    private var sunset: Long = 0
    private var dateTime: Long = 0

    fun setCity(city: String): CurrentWeatherBuilder {
        this.city = city
        return this
    }

    fun setCountry(country: String): CurrentWeatherBuilder {
        this.country = country
        return this
    }

    fun setDescription(description: String): CurrentWeatherBuilder {
        this.description = description
        return this
    }

    fun setIcon(icon: String): CurrentWeatherBuilder {
        this.icon = icon
        return this
    }

    fun setClouds(clouds: Int): CurrentWeatherBuilder {
        this.clouds = clouds
        return this
    }

    fun setHumidity(humidity: Int): CurrentWeatherBuilder {
        this.humidity = humidity
        return this
    }

    fun setPressure(pressure: Double): CurrentWeatherBuilder {
        this.pressure = pressure
        return this
    }

    fun setTempMax(tempMax: Double): CurrentWeatherBuilder {
        this.tempMax = tempMax
        return this
    }

    fun setTempMin(tempMin: Double): CurrentWeatherBuilder {
        this.tempMin = tempMin
        return this
    }

    fun setCurrentTemp(currentTemp: Double): CurrentWeatherBuilder {
        this.currentTemp = currentTemp
        return this
    }

    fun setWindSpeed(windSpeed: Double): CurrentWeatherBuilder {
        this.windSpeed = windSpeed
        return this
    }

    fun setSunrise(sunrise: Long): CurrentWeatherBuilder {
        this.sunrise = sunrise
        return this
    }

    fun setSunset(sunset: Long): CurrentWeatherBuilder {
        this.sunset = sunset
        return this
    }

    fun setDateTime(dateTime: Long): CurrentWeatherBuilder {
        this.dateTime = dateTime
        return this
    }

    fun build(): CurrentWeatherModel {
        return CurrentWeatherModel(
                city!!,
                country!!,
                description!!,
                icon!!,
                clouds,
                humidity,
                pressure,
                tempMax,
                tempMin,
                currentTemp,
                windSpeed,
                sunrise,
                sunset,
                dateTime
        )
    }
}