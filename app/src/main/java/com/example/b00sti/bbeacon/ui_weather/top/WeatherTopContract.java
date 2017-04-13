package com.example.b00sti.bbeacon.ui_weather.top;

import com.example.b00sti.bbeacon.ui_weather.top.model.WeatherFromOWM;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

class WeatherTopContract {

    public interface View {

        void refreshViews(WeatherFromOWM weatherFromOWM);

        void refreshViews(WeatherFromOWMRealm weatherFromOWMRealm);

    }

    public interface Presenter {

        void initLocation();

        void stopLocationListener();

        void getWeatherDataFromWeb();

        void initViews();
    }
}
