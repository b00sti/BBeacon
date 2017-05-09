package com.example.b00sti.bbeacon.ui_scanner.top;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-09
 */

@Getter
@Setter
public class BeaconLocation extends RealmObject {

    private long time;
    private String beaconId;
    private Double lat;
    private Double lng;

}
