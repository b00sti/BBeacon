package com.example.b00sti.bbeacon.ui_weather.model.utils;

import android.content.Context;

import com.example.b00sti.bbeacon.R;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-26
 */

public class WeatherConditionUtils {
    private WeatherConditionUtils() {
    }

    public static String getUnitsName(@WeatherParameterKind String kind, Context ctx) {
        switch (kind) {
            case WeatherParameterKind.WEATHER_HUMIDITY:
                return ctx.getResources().getString(R.string.humidity_unit);
            case WeatherParameterKind.WEATHER_PRESSURE:
                return ctx.getResources().getString(R.string.pressure_unit);
            case WeatherParameterKind.WEATHER_TEMPERATURE:
                return ctx.getResources().getString(R.string.temperature_unit);
            default:
                return "";
        }
    }

    public static String getParameterName(@WeatherParameterKind String kind, Context ctx) {
        switch (kind) {
            case WeatherParameterKind.WEATHER_HUMIDITY:
                return ctx.getResources().getString(R.string.humidity_key);
            case WeatherParameterKind.WEATHER_PRESSURE:
                return ctx.getResources().getString(R.string.pressure_key);
            case WeatherParameterKind.WEATHER_TEMPERATURE:
                return ctx.getResources().getString(R.string.temperature_key);
            default:
                return "";
        }
    }

    public static String getConditionName(@WeatherConditionKind String kind, Context ctx) {
        switch (kind) {
            case WeatherConditionKind.LESS:
                return ctx.getResources().getString(R.string.less);
            case WeatherConditionKind.MORE:
                return ctx.getResources().getString(R.string.more);
            case WeatherConditionKind.EQUAL:
                return ctx.getResources().getString(R.string.equal);
            default:
                return "";
        }
    }
}
