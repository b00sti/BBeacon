package com.example.b00sti.bbeacon.ui_weather.interactors;

import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.List;

/**
 * Created by b00sti on 16.03.2017.
 */

public class SetWeatherInteractor {

    private SetWeatherInteractor() {
    }

    public static void execute(List<WeatherItem> data) {
        RealmUtils.SaveAll(data, null);
    }

    public static void execute(WeatherItem weatherItem) {
        RealmUtils.SaveAll(weatherItem, null);
    }

    public static void execute(WeatherItem weatherItem, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAll(weatherItem, listener);
    }

    public static void execute(List<WeatherItem> data, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAll(data, listener);
    }

}
