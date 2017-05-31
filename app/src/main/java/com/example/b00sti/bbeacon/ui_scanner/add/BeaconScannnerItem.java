package com.example.b00sti.bbeacon.ui_scanner.add;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@Getter
@Setter
@AllArgsConstructor
public class BeaconScannnerItem extends RealmObject {
    @PrimaryKey
    String id;

    private String name;
    private String uuid;

    private int color;
    private boolean enabled;
    private String lastVisible;
    private int range;

    public BeaconScannnerItem() {

    }
}
