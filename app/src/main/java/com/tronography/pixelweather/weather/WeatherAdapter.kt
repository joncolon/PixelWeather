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
import com.tronography.pixelweather.model.ForecastModel
import com.tronography.pixelweather.utils.DateFormatter
import com.tronography.pixelweather.utils.IconUrlUtils


internal class WeatherAdapter(private val currentWeatherModel: CurrentWeatherModel?, private val forecast: List<ForecastModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            holder.bind(currentWeatherModel)
        } else if (holder is ForecastHolder) {
            val currentItem = getItem(position - 1)
            holder.bind(currentItem)
        }
    }

    private fun getItem(position: Int): ForecastModel {
        return forecast[position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_CURRENT_WEATHER else TYPE_FORECAST
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }


    override fun getItemCount(): Int {
        return forecast.size
    }

    internal inner class ForecastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dateTv: TextView? = null
        var tempMaxTv: TextView? = null
        var tempMinTv: TextView? = null
        var iconImage: ImageView? = null

        private val context: Context

        init {
            context = itemView.context
            dateTv = itemView.findViewById<TextView>(R.id.date_tv)
            tempMaxTv = itemView.findViewById<TextView>(R.id.temp_max_tv)
            tempMinTv = itemView.findViewById<TextView>(R.id.temp_min_tv)
            iconImage = itemView.findViewById<ImageView>(R.id.icon_iv)
        }

        fun bind(result: ForecastModel) {
            setDateTime(result)
            setTempMax(result)
            setTempMin(result)
            setIcon(result)
        }

        private fun setIcon(result: ForecastModel) {
            Glide.with(context)
                    .load(IconUrlUtils.getIconUrl(result.icon))
                    .into(iconImage!!)
        }

        private fun setTempMin(result: ForecastModel) {
            tempMinTv!!.text = result.tempMin.toInt().toString()
        }

        private fun setDateTime(result: ForecastModel) {
            dateTv!!.text = DateFormatter.forecastDateFormatter(result.dateTime)
        }

        private fun setTempMax(result: ForecastModel) {
            tempMaxTv!!.text = result.tempMax.toInt().toString()
        }
    }

    internal inner class CurrentWeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currentTempTv: TextView? = null
        var tempMinTv: TextView? = null
        var tempMaxTv: TextView? = null
        var cityTv: TextView? = null
        var countryTv: TextView? = null
        var descriptionTv: TextView? = null
        var iconImage: ImageView? = null

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

        fun bind(result: CurrentWeatherModel?) {
            setCurrentTemp(result)
            setDescription(result)
            setCity(result)
            setCountry(result)
            setTempMax(result)
            setTempMin(result)
            setIcon(result)
        }

        private fun setCurrentTemp(result: CurrentWeatherModel?) {
            currentTempTv!!.text = result!!.currentTemp.toInt().toString()
        }

        private fun setDescription(result: CurrentWeatherModel?) {
            descriptionTv!!.text = result!!.description
        }

        private fun setCity(result: CurrentWeatherModel?) {
            cityTv!!.text = result!!.city
        }

        private fun setCountry(result: CurrentWeatherModel?) {
            countryTv!!.text = result!!.country
        }

        private fun setIcon(result: CurrentWeatherModel?) {
            Glide.with(context)
                    .load(IconUrlUtils.getIconUrl(result!!.icon))
                    .into(iconImage!!)
        }

        private fun setTempMin(result: CurrentWeatherModel?) {
            tempMinTv!!.text = result!!.tempMin.toInt().toString()
        }

        private fun setTempMax(result: CurrentWeatherModel?) {
            tempMaxTv!!.text = result!!.tempMax.toInt().toString()
        }
    }

    companion object {

        private val TYPE_CURRENT_WEATHER = 0
        private val TYPE_FORECAST = 1
    }
}
