package com.tronography.pixelweather.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tronography.pixelweather.PixelWeatherApplication;
import com.tronography.pixelweather.R;
import com.tronography.pixelweather.http.OpenWeatherClient;
import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.utils.IconUrlUtils;
import com.tronography.pixelweather.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.tronography.pixelweather.utils.IconUrlUtils.*;

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
    @Bind(R.id.current_temp_tv)
    TextView currentTempTv;
    @Bind(R.id.temp_min_tv)
    TextView tempMinTv;
    @Bind(R.id.temp_max_tv)
    TextView tempMaxTv;
    @Bind(R.id.city_tv)
    TextView cityTv;
    @Bind(R.id.country_tv)
    TextView countryTv;
    @Bind(R.id.description_tv)
    TextView descriptionTv;
    @Bind(R.id.icon_iv)
    ImageView iconImage;
    @Bind(R.id.degree_symbol_tv)
    TextView degreeSymbol;
    @Bind(R.id.arrow_up_iv)
    ImageView arrowUpImage;
    @Bind(R.id.arrow_down_iv)
    ImageView arrowDownImage;

    private WeatherAdapter adapter;
    private Activity activity;
    private List<ForecastModel> results = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ((PixelWeatherApplication) getApplicationContext()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        activity = this;
        presenter.setView(this);
        adapter = new WeatherAdapter(results);
        forecast_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        forecast_rv.setAdapter(adapter);

        searchView.onActionViewExpanded();
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onQuerySubmitted(query);
                KeyboardUtils.hideSoftKeyboard(activity);
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
    public void showCurrentWeather(CurrentWeatherModel currentWeatherModel) {
        currentTempTv.setVisibility(VISIBLE);
        tempMinTv.setVisibility(VISIBLE);
        tempMaxTv.setVisibility(VISIBLE);
        iconImage.setVisibility(VISIBLE);
        cityTv.setVisibility(VISIBLE);
        countryTv.setVisibility(VISIBLE);
        degreeSymbol.setVisibility(VISIBLE);
        descriptionTv.setVisibility(VISIBLE);
        arrowDownImage.setVisibility(VISIBLE);
        arrowUpImage.setVisibility(VISIBLE);

        currentTempTv.setText((String.valueOf((int) currentWeatherModel.getCurrentTemp())));
        tempMinTv.setText((String.valueOf((int) currentWeatherModel.getTempMin())));
        tempMaxTv.setText((String.valueOf((int) currentWeatherModel.getTempMax())));
        descriptionTv.setText(currentWeatherModel.getDescription());
        cityTv.setText(currentWeatherModel.getCity());
        countryTv.setText(currentWeatherModel.getCountry());

        Glide.with(getApplicationContext())
                .load(getIconUrl(currentWeatherModel.getIcon()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iconImage);
    }


    @Override
    public void showForecast(List<ForecastModel> forecast) {
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
            currentTempTv.setVisibility(INVISIBLE);
            tempMaxTv.setVisibility(INVISIBLE);
            tempMinTv.setVisibility(INVISIBLE);
            iconImage.setVisibility(INVISIBLE);
            descriptionTv.setVisibility(INVISIBLE);
            cityTv.setVisibility(INVISIBLE);
            countryTv.setVisibility(INVISIBLE);
            degreeSymbol.setVisibility(INVISIBLE);
            arrowUpImage.setVisibility(INVISIBLE);
            arrowDownImage.setVisibility(INVISIBLE);
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
