package com.example.b00sti.bbeacon.ui_weather;

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
    String text;
    String time;
    String days;
    String humidity;
    String pressure;
    boolean isAlarm;
    int color;

    public WeatherItem() {
    }
}
