package com.example.b00sti.bbeacon.ui_scanner.interactors;

import com.example.b00sti.bbeacon.ui_scanner.top.BeaconLocation;
import com.example.b00sti.bbeacon.utils.RealmUtils;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-09
 */

public class SetLocationInteractor {

    public static void toDataBase(BeaconLocation beaconLocation) {
        RealmUtils.SaveAll(beaconLocation, null);
    }

}
