package com.example.b00sti.bbeacon.base;

import android.app.Application;

import com.example.b00sti.bbeacon.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public abstract class BaseApp<P extends RealmMigration> extends Application {
    private static final String TAG = "BaseApp";
    private static BaseApp instance;

    public static BaseApp getInstance() {
        return instance;
    }

    public abstract P setRealmMigration();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRealmConfiguration();
    }

    private void initRealmConfiguration() {
        int schemaVersion = setRealmSchemaVersion();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(getResources().getString(R.string.app_name) + ".realm")
                .schemaVersion(schemaVersion)
//                .deleteRealmIfMigrationNeeded()
                .migration(setRealmMigration())
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    protected abstract int setRealmSchemaVersion();
}
