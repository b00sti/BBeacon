package com.example.b00sti.bbeacon.ui_weather.pollution;

import com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

class PollutionContract {

    public interface View {

        void refreshWeatherViews(WeatherFromOWMRealm weatherFromOWMRealm);

        void refreshPollutionViews(PollutionRealm pollutionRealm);

    }

    public interface Presenter {

        void initLocation();

        void stopLocation();

        void initViews();
    }
}
