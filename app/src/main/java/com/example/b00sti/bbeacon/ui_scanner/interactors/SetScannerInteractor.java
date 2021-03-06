package com.example.b00sti.bbeacon.ui_scanner.interactors;

import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;
import com.example.b00sti.bbeacon.ui_weather.interactors.SetWeatherInteractor;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;
import com.example.b00sti.bbeacon.ui_weather.utils.WeatherConditionKind;
import com.example.b00sti.bbeacon.ui_weather.utils.WeatherParameterKind;
import com.example.b00sti.bbeacon.utils.NumUtils;
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
        //generate fake Weathter Item
        List<WeatherItem> weatherItems = new ArrayList<>();
        for (ScannerItem scannerItem : data) {
            WeatherItem weatherItem = new WeatherItem();
            weatherItem.setBeaconId(scannerItem.getId());
            weatherItem.setColor(scannerItem.getColor());
            weatherItem.setHumidity((new Random().nextInt(100)) + " %");
            weatherItem.setMessage("Hide flowers ");
            weatherItem.setAlarm(scannerItem.isEnabled());
            weatherItem.setPressure((new Random().nextInt(30) + 1000) + " hPa");
            weatherItem.setTemp((int) (Math.random() * 3) + 16 + "");
            weatherItem.setTitle(scannerItem.getTitle());
            weatherItem.setConditionKind(WeatherConditionKind.MORE);
            weatherItem.setConditionParameterKind(WeatherParameterKind.WEATHER_TEMPERATURE);
            weatherItem.setConditionParameterValue(NumUtils.toD(25));
            weatherItems.add(weatherItem);
        }
        SetWeatherInteractor.execute(weatherItems, null);
    }

}
