package com.example.b00sti.bbeacon.ui_weather.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.navigation.ActivityBuilder;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-27
 */

@EBean(scope = EBean.Scope.Singleton)
public class NotificationHelper {

    @RootContext
    Context ctx;

    private NotificationManager notificationManager;

    @AfterInject
    void init() {
        notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
    }

    public void sendTrackingAlarmNotifrication(ScannerItem scannerItem) {
        Intent intent = ActivityBuilder.buildStartBeaconDetailsActivityIntent(scannerItem, ctx);
        PendingIntent pIntent = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), intent, 0);

        String contentText = scannerItem.getTitle() + " is lost";
        String contentTitle = "Check connection";

        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.icon);

        Notification notification = new Notification.Builder(ctx)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setLargeIcon(icon)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .build();

        if (notification != null) {
            notificationManager.notify(0, notification);
        }
    }

    public void sendLowBatteryNotification(ScannerItem scannerItem) {
        Intent intent = ActivityBuilder.buildStartBeaconDetailsActivityIntent(scannerItem, ctx);
        PendingIntent pIntent = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), intent, 0);

        String contentText = "Low battery";
        String contentTitle = "Replace the battery in " + scannerItem.getTitle() + " beacon";

        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.icon);

        Notification notification = new Notification.Builder(ctx)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setLargeIcon(icon)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .build();

        if (notification != null) {
            notificationManager.notify(0, notification);
        }
    }

    public void sendWeatherNotification(WeatherItem weatherItem) {
        Intent intent = ActivityBuilder.buildStartWeatherDetailsActivityIntent(weatherItem, ctx);
        PendingIntent pIntent = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), intent, 0);

        String contentText = WeatherConditionUtils.prepareNotificationConditionsText(weatherItem, ctx, false);
        String contentTitle = weatherItem.getMessage();

        Notification notification = new Notification.Builder(ctx)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setLargeIcon(getIcon(weatherItem.getConditionParameterKind()))
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .build();

        if (notification != null) {
            notificationManager.notify(0, notification);
        }
    }

    private Bitmap getIcon(@WeatherParameterKind String kind) {
        return BitmapFactory.decodeResource(ctx.getResources(), WeatherConditionUtils.getParameterIcon(kind));
    }
}
