package com.example.b00sti.bbeacon.ui_weather.top;

import com.example.b00sti.bbeacon.utils.RealmUtils;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

public class SetWeatherFromOWMInteractor {
    public void execute(WeatherFromOWMRealm weatherFromOWMRealm) {
        RealmUtils.SaveAll(weatherFromOWMRealm, null);
    }
}
