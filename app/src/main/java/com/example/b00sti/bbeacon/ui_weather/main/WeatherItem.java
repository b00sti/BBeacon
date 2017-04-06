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
    String beaconId;
    String conditionValue;
    String conditionParameter;
    boolean isAlarm;
    int color;
    private String title;
    private String temp;
    private String message;
    private String humidity;
    private String pressure;

    public WeatherItem() {
    }
}
