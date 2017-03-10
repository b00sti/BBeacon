package com.example.b00sti.bbeacon.ui_scanner;

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
public class ScannerItem extends RealmObject {
    @PrimaryKey
    String text;

    int color;
    boolean enabled;
    String lastVisible;

    public ScannerItem() {
    }
}