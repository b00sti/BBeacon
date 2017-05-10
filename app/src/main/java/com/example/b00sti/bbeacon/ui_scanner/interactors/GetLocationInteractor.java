package com.example.b00sti.bbeacon.ui_scanner.interactors;

import com.example.b00sti.bbeacon.ui_scanner.top.BeaconLocation;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-09
 */

public class GetLocationInteractor {

    public static List<BeaconLocation> fromDatabase() {
        return RealmUtils.FindAll(BeaconLocation.class);
    }

}
