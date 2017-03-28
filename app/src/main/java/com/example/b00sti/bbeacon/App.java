package com.example.b00sti.bbeacon;

import android.util.Log;

import com.example.b00sti.bbeacon.base.BaseApp;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class App extends BaseApp<LastRealmMigration> {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public LastRealmMigration setRealmMigration() {
        return new LastRealmMigration();
    }

    @Override
    protected int setRealmSchemaVersion() {
        return 3;
    }

}
