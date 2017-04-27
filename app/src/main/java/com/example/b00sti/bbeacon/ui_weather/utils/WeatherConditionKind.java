package com.example.b00sti.bbeacon.ui_weather.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.b00sti.bbeacon.ui_weather.utils.WeatherConditionKind.EQUAL;
import static com.example.b00sti.bbeacon.ui_weather.utils.WeatherConditionKind.LESS;
import static com.example.b00sti.bbeacon.ui_weather.utils.WeatherConditionKind.MORE;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-26
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        LESS,
        MORE,
        EQUAL
})
public @interface WeatherConditionKind {
    String LESS = "less";
    String MORE = "more";
    String EQUAL = "equal";
}
