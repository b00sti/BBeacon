package com.example.b00sti.bbeacon.ui_weather.details;

import android.support.v7.widget.CardView;

import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

class WeatherDetailsContract {

    public interface View {

        void updateUI(WeatherItem weatherItem);

        void updateNotificationView(String text);

        CardView getChartCardView1();

        CardView getChartCardView2();

        CardView getChartCardView3();
    }

    public interface Presenter {

        void initUI(String id);

        String getNotifyConditionsToUI(WeatherItem weatherItem);

        String getNotifyConditionsToUI();

        void getNotifyConditionsFromUser();

        void onAccept();

        void onCancel();

    }
}
