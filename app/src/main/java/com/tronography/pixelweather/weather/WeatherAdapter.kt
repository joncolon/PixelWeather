package com.tronography.pixelweather.weather

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tronography.pixelweather.R
import com.tronography.pixelweather.model.CurrentWeatherModel
import com.tronography.pixelweather.model.FiveDayForecastModel
import com.tronography.pixelweather.utils.IconUrlUtils
import java.util.*


internal class WeatherAdapter(
        private val currentWeatherModel: CurrentWeatherModel?,
        private val fiveDayForecast: ArrayList<FiveDayForecastModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == TYPE_CURRENT_WEATHER) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.itemview_current_weather, parent, false)
            return CurrentWeatherHolder(v)
        } else if (viewType == TYPE_FORECAST) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.itemview_forecast, parent, false)
            return ForecastHolder(v)
        }
        return null
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CurrentWeatherHolder) {
            currentWeatherModel?.let { holder.bind(it) }
        } else if (holder is ForecastHolder) {
            val currentItem = getItem(position - 1)
            holder.bind(currentItem)
        }
    }

    private fun getItem(position: Int): FiveDayForecastModel {
        return fiveDayForecast[position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_CURRENT_WEATHER else TYPE_FORECAST
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }


    override fun getItemCount(): Int {
        val spaceForCurrentWeatherViewHolder = 1
        return fiveDayForecast.size + spaceForCurrentWeatherViewHolder
    }

    internal inner class ForecastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dateTv: TextView
        var tempMaxTv: TextView
        var tempMinTv: TextView
        var iconImage: ImageView

        private val context: Context

        init {
            context = itemView.context
            dateTv = itemView.findViewById<TextView>(R.id.date_tv)
            tempMaxTv = itemView.findViewById<TextView>(R.id.temp_max_tv)
            tempMinTv = itemView.findViewById<TextView>(R.id.temp_min_tv)
            iconImage = itemView.findViewById<ImageView>(R.id.icon_iv)
        }

        fun bind(result: FiveDayForecastModel) {
            setDateTime(result)
            setTempMax(result)
            setTempMin(result)
            setIcon(result)
        }

        private fun setIcon(result: FiveDayForecastModel) {
            Glide.with(context)
                    .load(IconUrlUtils.getIconUrl(result.icon))
                    .into(iconImage)
        }

        private fun setTempMin(result: FiveDayForecastModel) {
            tempMinTv.text = result.tempMin.toInt().toString()
        }

        private fun setDateTime(result: FiveDayForecastModel) {
            dateTv.text = result.dayOfWeek
        }

        private fun setTempMax(result: FiveDayForecastModel) {
            tempMaxTv.text = result.tempMax.toInt().toString()
        }
    }

    internal inner class CurrentWeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currentTempTv: TextView
        var tempMinTv: TextView
        var tempMaxTv: TextView
        var cityTv: TextView
        var countryTv: TextView
        var descriptionTv: TextView
        var iconImage: ImageView

        private val context: Context

        init {
            context = itemView.context
            currentTempTv = itemView.findViewById<TextView>(R.id.current_temp_tv)
            tempMinTv = itemView.findViewById<TextView>(R.id.temp_min_tv)
            tempMaxTv = itemView.findViewById<TextView>(R.id.temp_max_tv)
            cityTv = itemView.findViewById<TextView>(R.id.city_tv)
            countryTv = itemView.findViewById<TextView>(R.id.country_tv)
            descriptionTv = itemView.findViewById<TextView>(R.id.description_tv)
            iconImage = itemView.findViewById<ImageView>(R.id.icon_iv)
        }

        fun bind(currentWeather: CurrentWeatherModel) {
            setCurrentTemp(currentWeather)
            setDescription(currentWeather)
            setCity(currentWeather)
            setCountry(currentWeather)
            setTempMax(currentWeather)
            setTempMin(currentWeather)
            setIcon(currentWeather)
        }

        private fun setCurrentTemp(currentWeather: CurrentWeatherModel) {
            currentTempTv.text = currentWeather.currentTemp.toInt().toString()
        }

        private fun setDescription(currentWeather: CurrentWeatherModel) {
            descriptionTv.text = currentWeather.description
        }

        private fun setCity(currentWeather: CurrentWeatherModel) {
            cityTv.text = currentWeather.city
        }

        private fun setCountry(currentWeather: CurrentWeatherModel) {
            countryTv.text = currentWeather.country
        }

        private fun setIcon(currentWeather: CurrentWeatherModel) {
            Glide.with(context)
                    .load(IconUrlUtils.getIconUrl(currentWeather.icon))
                    .into(iconImage)
        }

        private fun setTempMin(currentWeather: CurrentWeatherModel) {
            tempMinTv.text = currentWeather.tempMin.toInt().toString()
        }

        private fun setTempMax(currentWeather: CurrentWeatherModel) {
            tempMaxTv.text = currentWeather.tempMax.toInt().toString()
        }
    }

    companion object {
        private val TYPE_CURRENT_WEATHER = 0
        private val TYPE_FORECAST = 1
    }
}
