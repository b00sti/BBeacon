package com.example.b00sti.bbeacon;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class LastRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            oldVersion++;
        }

    }
}