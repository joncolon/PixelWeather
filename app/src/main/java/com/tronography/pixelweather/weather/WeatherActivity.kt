package com.tronography.pixelweather.weather

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import com.tronography.pixelweather.PixelWeatherApplication
import com.tronography.pixelweather.R
import com.tronography.pixelweather.model.ForecastModel
import com.tronography.pixelweather.model.WeatherReport
import com.tronography.pixelweather.utils.KeyboardUtils
import java.util.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), Weather.View {

    @Inject
    lateinit var presenter: WeatherPresenter

    internal var searchView: SearchView? = null
    internal var forecast_rv: RecyclerView? = null
    internal var spinner: ProgressBar? = null
    internal var errorTv: TextView? = null

    private var activity: Activity? = null
    private val forecast = ArrayList<ForecastModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        (applicationContext as PixelWeatherApplication).appComponent!!.inject(this)

        activity = this
        presenter.setView(this)


        searchView = findViewById(R.id.search_view) as SearchView
        spinner = findViewById(R.id.weather_progress_spinner) as ProgressBar
        errorTv = findViewById(R.id.weather_error_tv) as TextView
        forecast_rv = findViewById(R.id.forecast_list) as RecyclerView

        searchView!!.onActionViewExpanded()
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isEmpty()) {
                    presenter.onQuerySubmitted(query)
                    KeyboardUtils.hideSoftKeyboard(activity as WeatherActivity)
                    searchView!!.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        presenter.showWeatherFromLastQueriedCity()
    }

    override fun onResume() {
        super.onResume()
        presenter.showWeatherFromLastQueriedCity()
        searchView!!.clearFocus()
    }

    override fun showWeatherReport(weatherReport: WeatherReport) {
        val adapter = WeatherAdapter(weatherReport.currentWeather, forecast)
        forecast_rv!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        forecast_rv!!.adapter = adapter
        forecast_rv!!.visibility = VISIBLE
        errorTv!!.visibility = GONE
        forecast.addAll(weatherReport.forecast!!)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading(show: Boolean) = if (show) {
        forecast_rv!!.visibility = GONE
        spinner!!.visibility = VISIBLE
    } else {
        spinner!!.visibility = GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.release()
    }

    override fun showError(error: String?) {
        errorTv!!.visibility = VISIBLE
        errorTv!!.text = error
    }
}
