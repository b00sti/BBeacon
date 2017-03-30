package com.example.b00sti.bbeacon.ui_alarm.interactors;

import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-30
 */

public class RemoveAlarmInteractor {

    public void execute(AlarmItem alarmItem) {
        RealmUtils.Remove(AlarmItem.class, "id", alarmItem.getId());
    }

}
