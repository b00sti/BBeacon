package com.example.b00sti.bbeacon.ui_scanner.interactors;

import android.util.Log;

import com.example.b00sti.bbeacon.ui_scanner.top.BeaconLocation;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-09
 */

public class GetLocationInteractor {
    private static final String TAG = "GetLocationInteractor";

    public static List<BeaconLocation> fromDatabase() {
        return RealmUtils.FindAll(BeaconLocation.class);
    }

    public static List<BeaconLocation> fromDatabase(int count) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<BeaconLocation> all = realm.where(BeaconLocation.class).findAllSorted("time", Sort.DESCENDING);
        Log.d(TAG, "fromDatabase: location size: " + all.size());
        if (all.size() > 300) {
            for (int i = 300; i < all.size(); i++) {
                realm.beginTransaction();
                all.deleteFromRealm(i);
                realm.commitTransaction();
            }
            Log.d(TAG, "fromDatabase: location size: " + all.size());
        }

        final List<BeaconLocation> list;
        if (all.size() < count) {
            list = realm.copyFromRealm(all);
        } else {
            list = realm.copyFromRealm(all.subList(0, count));
        }
        realm.close();
        return list;
    }
}
