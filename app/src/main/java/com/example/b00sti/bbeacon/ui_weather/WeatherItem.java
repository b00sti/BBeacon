package com.example.b00sti.bbeacon.ui_weather;

import io.realm.RealmObject;
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

    String text;

    public WeatherItem() {
    }
}
