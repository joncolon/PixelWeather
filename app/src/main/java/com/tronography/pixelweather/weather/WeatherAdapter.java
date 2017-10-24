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
import com.tronography.pixelweather.model.ForecastModel;
import com.tronography.pixelweather.utils.DateUtils;
import com.tronography.pixelweather.utils.IconUrlUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.tronography.pixelweather.utils.IconUrlUtils.*;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ForecastHolder> {
    private List<ForecastModel> results;

    public WeatherAdapter(List<ForecastModel> results) {
        this.results = results;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_forecast, parent, false);
        return new ForecastHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        final ForecastModel result = results.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
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
            dateTv.setText(DateUtils.dateFormatter(result.getDateTime()));
        }

        private void setTempMax(ForecastModel result) {
            tempMaxTv.setText(String.valueOf((int) result.getTempMax()));
        }


    }
}
