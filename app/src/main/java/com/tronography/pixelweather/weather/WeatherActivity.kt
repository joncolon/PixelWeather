package com.tronography.pixelweather.weather

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tronography.pixelweather.PixelWeatherApplication
import com.tronography.pixelweather.R
import com.tronography.pixelweather.common.BasePresenterActivity
import com.tronography.pixelweather.common.PresenterFactory
import com.tronography.pixelweather.model.FiveDayForecastModel
import com.tronography.pixelweather.model.WeatherReport
import com.tronography.pixelweather.utils.AppUtils
import com.tronography.pixelweather.utils.IconUrlUtils
import com.tronography.pixelweather.utils.KeyboardUtils
import java.util.*
import javax.inject.Inject


class WeatherActivity : BasePresenterActivity<WeatherPresenter, Weather.View>(), Weather.View {


    @Inject
    lateinit var weatherPresenterFactory: WeatherPresenterFactory

    private lateinit var linearLayout: LinearLayout
    private lateinit var searchView: SearchView
    private lateinit var forecastRecyclerView: RecyclerView
    private lateinit var spinner: ProgressBar
    private lateinit var errorTv: TextView
    private lateinit var dayLayout: ConstraintLayout
    private lateinit var presenter: WeatherPresenter

    override fun tag(): String {
        return "WeatherActivity : "
    }


    private val forecast = ArrayList<FiveDayForecastModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        linearLayout = findViewById(R.id.weather_activity_container)
        searchView = findViewById(R.id.search_view)
        spinner = findViewById(R.id.weather_progress_spinner)
        errorTv = findViewById(R.id.weather_error_tv)
        forecastRecyclerView = findViewById(R.id.forecast_list)
        showNightSkyBackground()

        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty().not()) {
                    presenter.onQuerySubmitted(query)
                    KeyboardUtils.hideSoftKeyboard(this@WeatherActivity)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        Log.d("WeatherActivity", "OnCreate: ")
    }

    override fun initializeDagger() {
        (applicationContext as PixelWeatherApplication).appComponent?.inject(this)
    }

    override fun getPresenterFactory(): PresenterFactory<WeatherPresenter> {
        return weatherPresenterFactory
    }

    override fun showNightSkyBackground() {
        linearLayout.setBackgroundResource(R.drawable.gradient_sky_night)
    }

    override fun onStart() {
        super.onStart()
        Log.d("WeatherActivity", "onStart: ")
        when (AppUtils.isOnline(this)) {
            true -> {
                Log.d("WeatherActivity", "presenter.showWeatherFromLastQueriedCity")
                presenter.showWeatherFromLastQueriedCity()
            }
            false -> {
                Log.d("WeatherActivity", "presenter.showCachedWeatherReport")
                presenter.showCachedWeatherReport()
            }
        }
    }

    override fun onPresenterCreatedOrRestored(presenter: WeatherPresenter) {
        this.presenter = presenter
        Log.d("WeatherActivity", "onPresenterCreatedOrRestored-" + tag()); }

    override fun onResume() {
        super.onResume()
        println("Has internet connection: ${AppUtils.isOnline(this)}")
        Log.d("WeatherActivity", "OnResume")


        searchView.clearFocus()
    }

    override fun showWeatherReport(weatherReport: WeatherReport) {
        Log.d("WeatherActivity", "showing Weather Report...")

        Log.d("WeatherActivity", "instantiating weather adapter ...")
        val adapter = WeatherAdapter(weatherReport.currentWeather, forecast)

        Log.d("WeatherActivity", "instantiating LinearLayoutManager...")
        forecastRecyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )

        Log.d("WeatherActivity", "scheduling RecyclerView layout animation...")
        forecastRecyclerView.scheduleLayoutAnimation()

        Log.d("WeatherActivity", "setting WeatherAdapter to RecyclerView...")
        forecastRecyclerView.adapter = adapter

        Log.d("WeatherActivity", "forecastRecyclerView visibility = ${forecastRecyclerView.visibility}")
        forecastRecyclerView.visibility = VISIBLE

        errorTv.visibility = GONE
        Log.d("WeatherActivity", "errorTv visibility = ${forecastRecyclerView.visibility}")

        showFiveDayForecast(weatherReport)
    }

    private fun showFiveDayForecast(weatherReport: WeatherReport) {
        Log.d("WeatherActivity", "showFiveDayForecast...")
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

    private fun loadForecastInto(layoutResId: Int, fiveDayForecast: FiveDayForecastModel) {
        dayLayout = findViewById(layoutResId)
        dayLayout.findViewById<TextView>(R.id.temp_max_tv).text = fiveDayForecast.tempMax.toInt().toString()
        dayLayout.findViewById<TextView>(R.id.temp_min_tv).text = fiveDayForecast.tempMin.toInt().toString()
        dayLayout.findViewById<TextView>(R.id.date_tv).text = fiveDayForecast.dayOfWeek

        Glide.with(this)
                .load(IconUrlUtils.getIconUrl(fiveDayForecast.icon))
                .into(dayLayout.findViewById(R.id.icon_iv))
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            forecastRecyclerView.visibility = GONE
            spinner.visibility = VISIBLE
        } else {
            spinner.visibility = GONE
        }
        Log.d("WeatherActivity", "forecastRecyclerView visibility = ${forecastRecyclerView.visibility}")
        Log.d("WeatherActivity", "spinner visibility = ${spinner.visibility}")

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
