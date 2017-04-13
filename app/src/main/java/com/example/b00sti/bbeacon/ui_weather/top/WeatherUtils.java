package com.example.b00sti.bbeacon.ui_weather.top;

import android.content.Context;

import com.example.b00sti.bbeacon.R;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

public class WeatherUtils {

    private WeatherUtils() {
    }

    public static String getFormattedTemp(double temp) {
        return String.valueOf((int) temp);
    }

    public static String getFormattedPressure(Context ctx, double pressure) {
        return String.format(ctx.getString(R.string.pressure), String.valueOf((int) pressure));
    }

    public static String getFormattedHumidity(Context ctx, int humidity) {
        return String.format(ctx.getString(R.string.humidity), String.valueOf((int) humidity));
    }

    public static String getFormattedWind(Context ctx, double wind) {
        return String.format(ctx.getString(R.string.wind_speed), String.valueOf((int) wind));
    }
}
