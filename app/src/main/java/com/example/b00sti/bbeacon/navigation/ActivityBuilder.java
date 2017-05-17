package com.example.b00sti.bbeacon.navigation;

import android.content.Context;
import android.content.Intent;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseInnerViewActivity_;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-27
import com.example.b00sti.bbeacon.utils.FragmentBuilder;

/**
 * Created by b00sti on 28.04.2017
 */

public class ActivityBuilder {

    public static void startDisableAlarmActivity(Context ctx, String id) {
        Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
        intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.DISABLE_ALARM);
        intent.putExtra("id", id);
        ctx.startActivity(intent);

    public static void startWeatherDetailsActivity(WeatherItem weatherItem, Context ctx) {
        Intent intent = buildStartWeatherDetailsActivityIntent(weatherItem, ctx);
        ctx.startActivity(intent);
    }

    public static void startBeaconDetailsActivity(ScannerItem scannerItem, Context ctx) {
        Intent intent = buildStartBeaconDetailsActivityIntent(scannerItem, ctx);
        ctx.startActivity(intent);
    }

    public static Intent buildStartWeatherDetailsActivityIntent(WeatherItem weatherItem, Context ctx) {
        Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
        intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.WEATHER_DETAILS);
        intent.putExtra("id", weatherItem.getBeaconId());
        return intent;
    }

    public static Intent buildStartBeaconDetailsActivityIntent(ScannerItem scannerItem, Context ctx) {
        Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
        intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.BEACON_DETAILS);
        intent.putExtra("id", scannerItem.getId());
        return intent;
    }
}
