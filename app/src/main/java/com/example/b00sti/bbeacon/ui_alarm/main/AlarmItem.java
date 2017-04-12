package com.example.b00sti.bbeacon.ui_alarm.main;

import com.example.b00sti.bbeacon.base.Bool;
import com.example.b00sti.bbeacon.base.SettingId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@Getter
@Setter
@AllArgsConstructor
public class AlarmItem extends RealmObject implements SettingId {

    @PrimaryKey
    long id;

    @NonNull String text;
    int color;
    @NonNull String time;
    boolean isEnabled = false;
    RealmList<Bool> isEnabledRepeat = new RealmList<>();

    public AlarmItem() {
        for (int i = 0; i < 7; i++) {
            isEnabledRepeat.add(new Bool(false));
        }
    }

    public AlarmItem(String text, int color, String time, boolean isEnabled) {
        this.text = text;
        this.color = color;
        this.time = time;
        this.isEnabled = isEnabled;
        for (int i = 0; i < 7; i++) {
            isEnabledRepeat.add(new Bool(false));
        }
    }

    @Override
    public void setManualId(long id) {
        setId(id);
    }
}
