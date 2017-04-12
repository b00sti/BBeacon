package com.example.b00sti.bbeacon.ui_weather.top;

import com.example.b00sti.bbeacon.utils.RealmUtils;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

public class GetWeatherFromOWMInteractor {
    public WeatherFromOWMRealm execute() {
        return RealmUtils.Find(WeatherFromOWMRealm.class, "id", 0); //NON-NLS
    }
}
