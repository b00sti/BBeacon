package com.example.b00sti.bbeacon.ui_weather.top;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

@Getter
@Setter
public class WeatherFromOWMRealm extends RealmObject {

    @PrimaryKey
    private int id = 0;

    private double temp;
    private double pressure;
    private int humidity;
    private double wind;
    private String icon;
    private String name;
    private double lat;
    private double lon;

    public WeatherFromOWMRealm() {
    }
}
