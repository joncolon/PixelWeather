package com.tronography.pixelweather.weather

import android.app.Activity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tronography.pixelweather.PixelWeatherApplication
import com.tronography.pixelweather.R
import com.tronography.pixelweather.model.FiveDayForecastModel
import com.tronography.pixelweather.model.WeatherReport
import com.tronography.pixelweather.utils.AppUtils
import com.tronography.pixelweather.utils.IconUrlUtils
import com.tronography.pixelweather.utils.KeyboardUtils
import java.util.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), Weather.View {

    @Inject
    lateinit var presenter: WeatherPresenter

    lateinit var searchView: SearchView
    lateinit var forecast_rv: RecyclerView
    lateinit var spinner: ProgressBar
    lateinit var errorTv: TextView

    lateinit var dayLayout: ConstraintLayout

    private var activity: Activity? = null
    private val forecast = ArrayList<FiveDayForecastModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        (applicationContext as PixelWeatherApplication).appComponent?.inject(this)

        activity = this
        presenter.setView(this)





        searchView = findViewById<SearchView>(R.id.search_view)
        spinner = findViewById<ProgressBar>(R.id.weather_progress_spinner)
        errorTv = findViewById<TextView>(R.id.weather_error_tv)
        forecast_rv = findViewById<RecyclerView>(R.id.forecast_list)

        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty().not()) {
                    presenter.onQuerySubmitted(query)
                    KeyboardUtils.hideSoftKeyboard(activity as WeatherActivity)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


    }

    override fun onResume() {
        super.onResume()
        println("Has internet connection: ${AppUtils.isOnline(this)}")
        when(AppUtils.isOnline(this)){
            true -> presenter.showWeatherFromLastQueriedCity()
            false -> presenter.showCachedWeatherReport()
        }
        searchView.clearFocus()
    }

    override fun showWeatherReport(weatherReport: WeatherReport) {
        val adapter = WeatherAdapter(weatherReport.currentWeather, forecast)
        forecast_rv.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        forecast_rv.visibility = VISIBLE
        forecast_rv.scheduleLayoutAnimation()
        forecast_rv.adapter = adapter
        errorTv.visibility = GONE

        showFiveDayForecast(weatherReport)
    }

    private fun showFiveDayForecast(weatherReport: WeatherReport) {
        for ((index, fiveDayForecast: FiveDayForecastModel) in weatherReport.fiveDayForecast.withIndex()) {
            Log.d("fivedayforecast = ", fiveDayForecast.dayOfWeek)
            when (index) {
                0 -> loadForecastInto(R.id.day_one, fiveDayForecast)
                1 -> loadForecastInto(R.id.day_two, fiveDayForecast)
                2 -> loadForecastInto(R.id.day_three, fiveDayForecast)
                3 -> loadForecastInto(R.id.day_four, fiveDayForecast)
                4 -> loadForecastInto(R.id.day_five, fiveDayForecast)
            }
        }
    }

    private fun loadForecastInto(resourceId: Int, fiveDayForecast: FiveDayForecastModel) {
        dayLayout = findViewById<ConstraintLayout>(resourceId)
        dayLayout.findViewById<TextView>(R.id.temp_max_tv).text = fiveDayForecast.tempMax.toInt().toString()
        dayLayout.findViewById<TextView>(R.id.temp_min_tv).text = fiveDayForecast.tempMin.toInt().toString()
        dayLayout.findViewById<TextView>(R.id.date_tv).text = fiveDayForecast.dayOfWeek

        Glide.with(this)
                .load(IconUrlUtils.getIconUrl(fiveDayForecast.icon))
                .into(dayLayout.findViewById<ImageView>(R.id.icon_iv))
    }

    override fun showLoading(show: Boolean) = if (show) {
        forecast_rv.visibility = GONE
        spinner.visibility = VISIBLE
    } else {
        spinner.visibility = GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.release()
    }

    override fun showError(error: String?) {
        errorTv.visibility = VISIBLE
        errorTv.text = error
    }
}
