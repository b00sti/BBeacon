package com.example.b00sti.bbeacon.ui_weather.details;

import android.support.v7.widget.CardView;

import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

class WeatherDetailsContract {

    public interface View {

        void updateUI(WeatherItem weatherItem);

        CardView getChartCardView1();

        CardView getChartCardView2();

        CardView getChartCardView3();
    }

    public interface Presenter {

        void initUI(String id);

        void onAccept();

        void onCancel();

    }
}
