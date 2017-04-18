package com.example.b00sti.bbeacon.ui_weather.top.interactors;

import com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm;
import com.example.b00sti.bbeacon.utils.RealmUtils;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

public class SetWeatherFromOWMInteractor {
    private SetWeatherFromOWMInteractor() {
    }

    public static void saveToRealm(WeatherFromOWMRealm weatherFromOWMRealm) {
        RealmUtils.SaveAll(weatherFromOWMRealm, null);
    }
}
