package com.example.b00sti.bbeacon.ui_weather.main;

import com.example.b00sti.bbeacon.ui_weather.utils.WeatherConditionKind;
import com.example.b00sti.bbeacon.ui_weather.utils.WeatherParameterKind;

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
    private Double conditionParameterValue;
    @WeatherParameterKind
    private String conditionParameterKind;
    @WeatherConditionKind
    private String conditionKind;
    private String title;
    private String temp;
    private String message;
    private String humidity;
    private String pressure;
    private boolean isAlarm;
    private int color;

    public WeatherItem() {
    }

    @WeatherParameterKind
    public String getConditionParameterKind() {
        return conditionParameterKind;
    }

    public void setConditionParameterKind(@WeatherParameterKind String conditionParameterKind) {
        this.conditionParameterKind = conditionParameterKind;
    }

    @WeatherConditionKind
    public String getConditionKind() {
        return conditionKind;
    }

    public void setConditionKind(@WeatherConditionKind String conditionKind) {
        this.conditionKind = conditionKind;
    }
}
