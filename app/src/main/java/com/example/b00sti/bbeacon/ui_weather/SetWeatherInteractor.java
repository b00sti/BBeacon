package com.example.b00sti.bbeacon.ui_weather;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b00sti on 16.03.2017.
 */

public class SetWeatherInteractor {

    public void execute(List<WeatherItem> data) {
        RealmUtils.SaveAll(data, null);
    }

    public void execute(WeatherItem scannerItem) {
        List<WeatherItem> data = new ArrayList<>();
        data.add(scannerItem);
        RealmUtils.SaveAll(data, null);
    }

    public void execute(WeatherItem scannerItem, RealmUtils.OnSuccessListener listener) {
        List<WeatherItem> data = new ArrayList<>();
        data.add(scannerItem);
        RealmUtils.SaveAll(data, listener);
    }

    public void execute(List<WeatherItem> data, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAll(data, listener);
    }

}
