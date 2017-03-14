package com.example.b00sti.bbeacon.ui_alarm;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class SetAlarmInteractor {

    public void execute(List<AlarmItem> data) {
        RealmUtils.SaveAll(data, null);
    }

    public void execute(AlarmItem alarmItem) {
        List<AlarmItem> data = new ArrayList<>();
        data.add(alarmItem);
        RealmUtils.SaveAll(data, null);
    }

    public void execute(AlarmItem alarmItem, RealmUtils.OnSuccessListener listener) {
        List<AlarmItem> data = new ArrayList<>();
        data.add(alarmItem);
        RealmUtils.SaveAll(data, listener);
    }

    public void execute(List<AlarmItem> data, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAll(data, listener);
    }

}
