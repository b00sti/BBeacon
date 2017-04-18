package com.example.b00sti.bbeacon.ui_weather.top;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

class WeatherTopContract {

    public interface View {

        void refreshViews(WeatherFromOWMRealm weatherFromOWMRealm);

    }

    public interface Presenter {

        void initLocation();

        void stopLocation();

        void initViews();
    }
}
