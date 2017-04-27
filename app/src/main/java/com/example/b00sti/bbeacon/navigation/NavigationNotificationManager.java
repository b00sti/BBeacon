package com.example.b00sti.bbeacon.navigation;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.b00sti.bbeacon.R;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-08
 */

public class NavigationNotificationManager {
    public static AHNotification newDefault(Context ctx, String s) {
        return new AHNotification.Builder()
                .setText(s)
                .setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(ctx, R.color.icons))
                .build();
    }
}
