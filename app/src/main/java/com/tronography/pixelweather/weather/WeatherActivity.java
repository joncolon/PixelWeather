package com.tronography.pixelweather.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tronography.pixelweather.PixelWeatherApplication;
import com.tronography.pixelweather.R;
import com.tronography.pixelweather.http.OpenWeatherClient;
import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WeatherActivity extends AppCompatActivity implements Weather.View {

    private static final String TAG = WeatherActivity.class.getSimpleName();

    @Inject
    OpenWeatherClient client;
    @Inject
    WeatherPresenter presenter;

    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.forecast_list)
    RecyclerView forecast_rv;
    @Bind(R.id.weather_progress_spinner)
    ProgressBar spinner;
    @Bind(R.id.weather_error_tv)
    TextView errorTv;

    private WeatherAdapter adapter;
    private Activity activity;
    private List<ForecastModel> results = new ArrayList<>();

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
                presenter.onQuerySubmitted(query);
                KeyboardUtils.hideSoftKeyboard(activity);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        presenter.checkLastQueriedCity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();
        presenter.checkLastQueriedCity();
    }

    @Override
    public void showWeatherReport(CurrentWeatherModel currentWeatherModel, List<ForecastModel> forecast) {
        adapter = new WeatherAdapter(currentWeatherModel, results);
        forecast_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        forecast_rv.setAdapter(adapter);

        forecast_rv.setVisibility(VISIBLE);
        errorTv.setVisibility(GONE);
        this.results.clear();
        this.results.addAll(forecast);
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
}
