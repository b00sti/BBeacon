package com.example.b00sti.bbeacon.ui_weather.main;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@Getter
@Setter
@AllArgsConstructor
public class WeatherItem extends RealmObject {

    @PrimaryKey
    private String beaconId;
    private String conditionValue;
    private String conditionParameter;

    private String title;
    private String temp;
    private String message;
    private String humidity;
    private String pressure;
    private boolean isAlarm;
    private int color;

    public WeatherItem() {
    }
}
