package com.example.b00sti.bbeacon.ui_weather.utils;

import android.content.Context;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;
import com.example.b00sti.bbeacon.utils.NumUtils;

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

    public static String prepareNotificationConditionsText(WeatherItem weatherItem, Context ctx) {
        return prepareNotificationConditionsText(weatherItem, ctx, true);
    }

    public static String prepareNotificationConditionsText(WeatherItem weatherItem, Context ctx, boolean withInitialPart) {
        StringBuilder builder = new StringBuilder();
        if (withInitialPart) {
            builder.append("Notify if ");
        }
        if (weatherItem.getConditionKind() != null && weatherItem.getConditionParameterValue() != null && weatherItem.getConditionParameterKind() != null) {
            builder.append(WeatherConditionUtils.getParameterName(weatherItem.getConditionParameterKind(), ctx));
            builder.append(" is ");
            builder.append(WeatherConditionUtils.getConditionName(weatherItem.getConditionKind(), ctx));
            builder.append(" ");
            if (!WeatherConditionKind.EQUAL.equals(weatherItem.getConditionKind())) {
                builder.append("than ");
            }
            builder.append(NumUtils.toS(weatherItem.getConditionParameterValue()));
            builder.append(WeatherConditionUtils.getUnitsName(weatherItem.getConditionParameterKind(), ctx));
            String result = builder.toString();
            return result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
        }

        return "-";
    }

    public static int getParameterIcon(@WeatherParameterKind String kind) {
        switch (kind) {
            case WeatherParameterKind.WEATHER_HUMIDITY:
                return R.drawable.weather2;
            case WeatherParameterKind.WEATHER_PRESSURE:
                return R.drawable.weather3;
            case WeatherParameterKind.WEATHER_TEMPERATURE:
                return R.drawable.weather1;
            default:
                return 0;
        }
    }
}
