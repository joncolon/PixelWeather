package com.tronography.pixelweather.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tronography.pixelweather.R;
import com.tronography.pixelweather.model.CurrentWeatherModel;
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.utils.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.tronography.pixelweather.utils.IconUrlUtils.getIconUrl;


class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ForecastModel> forecast;
    private CurrentWeatherModel currentWeatherModel;

    private static final int TYPE_CURRENT_WEATHER = 0;
    private static final int TYPE_FORECAST = 1;

    WeatherAdapter(CurrentWeatherModel currentWeatherModel, List<ForecastModel> forecast) {
        this.currentWeatherModel = currentWeatherModel;
        this.forecast = forecast;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CURRENT_WEATHER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_current_weather, parent, false);
            return new CurrentWeatherHolder(v);
        } else if (viewType == TYPE_FORECAST) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_forecast, parent, false);
            return new ForecastHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CurrentWeatherHolder) {
            CurrentWeatherHolder VHcurrentWeather = (CurrentWeatherHolder) holder;
            VHcurrentWeather.bind(currentWeatherModel);
        } else if (holder instanceof ForecastHolder) {
            ForecastModel currentItem = getItem(position - 1);
            ForecastHolder VHforecast = (ForecastHolder) holder;
            VHforecast.bind(currentItem);
        }
    }

    private ForecastModel getItem(int position) {
        return forecast.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_CURRENT_WEATHER;
        return TYPE_FORECAST;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public int getItemCount() {
        return forecast.size();
    }

    class ForecastHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.date_tv)
        TextView dateTv;
        @Bind(R.id.temp_max_tv)
        TextView tempMaxTv;
        @Bind(R.id.temp_min_tv)
        TextView tempMinTv;
        @Bind(R.id.icon_iv)
        ImageView iconImage;

        private Context context;

        ForecastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        void bind(final ForecastModel result) {
            setDateTime(result);
            setTempMax(result);
            setTempMin(result);
            setIcon(result);
        }

        private void setIcon(ForecastModel result) {
            Glide.with(context)
                    .load(getIconUrl(result.getIcon()))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iconImage);
        }

        private void setTempMin(ForecastModel result) {
            tempMinTv.setText(String.valueOf((int) result.getTempMin()));
        }

        private void setDateTime(ForecastModel result) {
            dateTv.setText(DateUtils.forecastDateFormatter(result.getDateTime()));
        }

        private void setTempMax(ForecastModel result) {
            tempMaxTv.setText(String.valueOf((int) result.getTempMax()));
        }
    }

    class CurrentWeatherHolder extends RecyclerView.ViewHolder {

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

        private Context context;

        CurrentWeatherHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        void bind(final CurrentWeatherModel result) {
            setCurrentTemp(result);
            setDescription(result);
            setCity(result);
            setCountry(result);
            setTempMax(result);
            setTempMin(result);
            setIcon(result);
        }

        private void setCurrentTemp(CurrentWeatherModel result) {
            currentTempTv.setText((String.valueOf((int) result.getCurrentTemp())));
        }

        private void setDescription(CurrentWeatherModel result) {
            descriptionTv.setText(result.getDescription());
        }

        private void setCity(CurrentWeatherModel result) {
            cityTv.setText(result.getCity());
        }

        private void setCountry(CurrentWeatherModel result) {
            countryTv.setText(result.getCountry());
        }

        private void setIcon(CurrentWeatherModel result) {
            Glide.with(context)
                    .load(getIconUrl(result.getIcon()))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iconImage);
        }

        private void setTempMin(CurrentWeatherModel result) {
            tempMinTv.setText(String.valueOf((int) result.getTempMin()));
        }

        private void setTempMax(CurrentWeatherModel result) {
            tempMaxTv.setText(String.valueOf((int) result.getTempMax()));
        }
    }
}
