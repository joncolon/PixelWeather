package com.tronography.pixelweather.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tronography.pixelweather.PixelWeatherApplication;
import com.tronography.pixelweather.R;
import com.tronography.pixelweather.http.OpenWeatherClient;
import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.model.WeatherReport;
import com.tronography.pixelweather.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WeatherActivity extends AppCompatActivity implements Weather.View {

    private static final String TAG = WeatherActivity.class.getSimpleName();

    @Inject
    OpenWeatherClient client;

    @Inject
    WeatherPresenter presenter;

    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.forecast_list)
    RecyclerView forecast_rv;
    @BindView(R.id.weather_progress_spinner)
    ProgressBar spinner;
    @BindView(R.id.weather_error_tv)
    TextView errorTv;

    private Activity activity;
    private List<ForecastModel> forecast = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ((PixelWeatherApplication) getApplicationContext()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        activity = this;
        presenter.setView(this);

        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    presenter.onQuerySubmitted(query);
                    KeyboardUtils.hideSoftKeyboard(activity);
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        presenter.showWeatherFromLastQueriedCity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.showWeatherFromLastQueriedCity();
        searchView.clearFocus();
    }

    @Override
    public void showWeatherReport(WeatherReport weatherReport) {
        WeatherAdapter adapter = new WeatherAdapter(weatherReport.getCurrentWeather(), forecast);
        forecast_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        forecast_rv.setAdapter(adapter);
        forecast_rv.setVisibility(VISIBLE);
        errorTv.setVisibility(GONE);
        forecast.addAll(weatherReport.getForecast());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            forecast_rv.setVisibility(GONE);
            spinner.setVisibility(VISIBLE);
        } else {
            spinner.setVisibility(GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }

    @Override
    public void showError(String error) {
        errorTv.setVisibility(VISIBLE);
        errorTv.setText(error);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
