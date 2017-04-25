package com.example.b00sti.bbeacon.ui_weather.model.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.b00sti.bbeacon.ui_weather.model.utils.WeatherParameterKind.WEATHER_HUMIDITY;
import static com.example.b00sti.bbeacon.ui_weather.model.utils.WeatherParameterKind.WEATHER_PRESSURE;
import static com.example.b00sti.bbeacon.ui_weather.model.utils.WeatherParameterKind.WEATHER_TEMPERATURE;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-25
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        WEATHER_HUMIDITY,
        WEATHER_PRESSURE,
        WEATHER_TEMPERATURE
})
public @interface WeatherParameterKind {
    String WEATHER_HUMIDITY = "humidity";
    String WEATHER_PRESSURE = "pressure";
    String WEATHER_TEMPERATURE = "temperature";
}


