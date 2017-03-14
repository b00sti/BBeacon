package com.example.b00sti.bbeacon.ui_alarm;

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
public class AlarmItem extends RealmObject {

    @PrimaryKey
    String text;

    boolean isEnabled = false;
    int color;


    public AlarmItem() {
    }
}
