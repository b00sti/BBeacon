package com.example.b00sti.bbeacon.ui_weather.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.BaseInnerViewActivity_;
import com.example.b00sti.bbeacon.base.ViewWrapper;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */
@EBean
public class WeatherAdapter extends BaseAdapter<WeatherItem, WeatherItemView> {
    private static final String TAG = "WeatherAdapter";

    @RootContext
    Context context;

    @Override
    protected WeatherItemView onCreateItemView(ViewGroup parent, int viewType) {
        return WeatherItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<WeatherItemView> holder, final int position) {
        final WeatherItemView weatherItemView = holder.getView();
        final WeatherItem weatherItem = dataSet.get(position);
        weatherItemView.bind(weatherItem);

        weatherItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWeatherDetailsActivity(weatherItem);
            }
        });
    }

    private void startWeatherDetailsActivity(WeatherItem weatherItem) {
        Intent intent = new Intent(context, BaseInnerViewActivity_.class);
        intent.putExtra(context.getString(R.string.bundle_fragment), FragmentBuilder.WEATHER_DETAILS);
        intent.putExtra("id", weatherItem.getBeaconId());
        context.startActivity(intent);
    }
}
