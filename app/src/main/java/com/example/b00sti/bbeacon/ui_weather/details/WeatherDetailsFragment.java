package com.example.b00sti.bbeacon.ui_weather.details;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

@EFragment(R.layout.weather_details)
public class WeatherDetailsFragment extends BaseFragment<WeatherDetailsPresenter> implements WeatherDetailsContract.View {

    @ViewById(R.id.card_view1) CardView card_view1;
    @ViewById(R.id.card_view2) CardView card_view2;
    @ViewById(R.id.card_view3) CardView card_view3;
    @ViewById(R.id.tempValueTV) TextView tempValueTV;
    @ViewById(R.id.pressureTV) TextView pressureTV;
    @ViewById(R.id.humidityTV) TextView humidityTV;
    @ViewById(R.id.messageTV) TextView messageTV;
    @ViewById(R.id.whenTV) TextView whenTV;
    @ViewById(R.id.titleTV) TextView titleTV;

    @Bean
    WeatherDetailsPresenter weatherDetailsPresenter;

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment_();
    }

    @Override
    protected WeatherDetailsPresenter registerPresenter() {
        weatherDetailsPresenter.attachView(this);
        return weatherDetailsPresenter;
    }

    @AfterViews
    void initUI() {
        Bundle bundle = getArguments();

        if (bundle.containsKey("id")) {
            String id = bundle.getString("id");
            presenter.initUI(id);
        }
    }

    @Override
    public void updateUI(WeatherItem weatherItem) {
        tempValueTV.setText(weatherItem.getTemp());
        pressureTV.setText(weatherItem.getPressure());
        humidityTV.setText(weatherItem.getHumidity());
        messageTV.setText(weatherItem.getMessage());
        String notifyConditions = presenter.getNotifyConditions(weatherItem);
        whenTV.setText(notifyConditions);
        titleTV.setText(weatherItem.getTitle());
    }

    @Override
    public CardView getChartCardView1() {
        return card_view1;
    }

    @Override
    public CardView getChartCardView2() {
        return card_view2;
    }

    @Override
    public CardView getChartCardView3() {
        return card_view3;
    }

    @Click(R.id.cancelIV)
    void cancel() {
        presenter.onCancel();
    }

    @Click(R.id.doneIV)
    void save() {
        presenter.onAccept();
    }

}