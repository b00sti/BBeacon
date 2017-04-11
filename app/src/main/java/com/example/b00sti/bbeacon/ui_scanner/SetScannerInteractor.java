package com.example.b00sti.bbeacon.ui_scanner;

import com.example.b00sti.bbeacon.ui_weather.interactors.SetWeatherInteractor;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-15
 */

public class SetScannerInteractor {

    public void execute(List<ScannerItem> data) {
        setWeatherItem(data);
        RealmUtils.SaveAll(data, null);
    }

    public void execute(ScannerItem scannerItem) {
        List<ScannerItem> scannerItems = new ArrayList<>();
        scannerItems.add(scannerItem);
        setWeatherItem(scannerItems);
        RealmUtils.SaveAll(scannerItem, null);
    }

    public void execute(ScannerItem scannerItem, RealmUtils.OnSuccessListener listener) {
        List<ScannerItem> scannerItems = new ArrayList<>();
        scannerItems.add(scannerItem);
        setWeatherItem(scannerItems);
        RealmUtils.SaveAll(scannerItem, listener);
    }

    public void execute(List<ScannerItem> data, RealmUtils.OnSuccessListener listener) {
        setWeatherItem(data);
        RealmUtils.SaveAll(data, listener);
    }

    private void setWeatherItem(List<ScannerItem> data) {
        List<WeatherItem> weatherItems = new ArrayList<>();
        for (ScannerItem scannerItem : data) {
            WeatherItem weatherItem = new WeatherItem();
            weatherItem.setBeaconId(scannerItem.getId());
            weatherItem.setColor(scannerItem.getColor());
            weatherItem.setConditionParameter("");
            weatherItem.setConditionValue("");
            weatherItem.setHumidity((new Random().nextInt(100)) + " %");
            weatherItem.setMessage("Do something !");
            weatherItem.setAlarm(scannerItem.isEnabled());
            weatherItem.setPressure((new Random().nextInt(30) + 1000) + " hPa");
            weatherItem.setTemp(new Random().nextInt(33) + " ℃");
            weatherItem.setTitle(scannerItem.getTitle());
            weatherItems.add(weatherItem);
        }
        new SetWeatherInteractor().execute(weatherItems, null);
    }

}
