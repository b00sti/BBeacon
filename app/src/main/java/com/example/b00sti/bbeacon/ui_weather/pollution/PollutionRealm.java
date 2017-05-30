package com.example.b00sti.bbeacon.ui_weather.pollution;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by b00sti on 30.05.2017
 */

@Getter
@Setter
public class PollutionRealm extends RealmObject {

    @PrimaryKey
    private int id = 0;

    private double aqi;
    private double idx;
    private String time;
    private String name;
    private double pm10;
    private double pm25;
    private double co;
    private double h;
    private double no2;
    private double p;

}
