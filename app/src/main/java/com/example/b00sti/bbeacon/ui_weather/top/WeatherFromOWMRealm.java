package com.example.b00sti.bbeacon.ui_weather.top;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

@Getter
@Setter
@AllArgsConstructor
public class WeatherFromOWMRealm extends RealmObject {

    @PrimaryKey
    int id = 0;

    double temp;
    double pressure;
    int humidity;
    double wind;
    String icon;
    String name;
    double lat;
    double lon;

    public WeatherFromOWMRealm() {
    }
}
