package com.example.b00sti.bbeacon.ui_weather.main;

import android.content.Context;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */
@EBean
public class WeatherAdapter extends BaseAdapter<WeatherItem, WeatherItemView> {

    @RootContext
    Context context;

    @Override
    protected WeatherItemView onCreateItemView(ViewGroup parent, int viewType) {
        return WeatherItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<WeatherItemView> holder, int position) {
        WeatherItemView weatherItemView = holder.getView();
        WeatherItem weatherItem = dataSet.get(position);
        weatherItemView.bind(weatherItem);
    }
}
