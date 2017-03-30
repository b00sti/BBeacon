package com.example.b00sti.bbeacon.ui_alarm.interactors;

import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class SetAlarmInteractor {

    public void execute(List<AlarmItem> data) {
        RealmUtils.SaveAll(data, null);
    }

    public void execute(AlarmItem alarmItem) {
        RealmUtils.SaveAll(alarmItem, null);
    }

    public void execute(AlarmItem alarmItem, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAll(alarmItem, listener);
    }

    public void execute(List<AlarmItem> data, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAll(data, listener);
    }

    public void executeWithId(AlarmItem alarmItem, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAllWithId(alarmItem, listener);
    }

}
