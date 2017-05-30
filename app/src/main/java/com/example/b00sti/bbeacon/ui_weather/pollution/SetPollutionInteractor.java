package com.example.b00sti.bbeacon.ui_weather.pollution;

import com.example.b00sti.bbeacon.utils.RealmUtils;

/**
 * Created by b00sti on 30.05.2017
 */

public class SetPollutionInteractor {

    private SetPollutionInteractor() {
    }

    public static void saveToRealm(PollutionRealm pollutionRealm) {
        RealmUtils.SaveAll(pollutionRealm, null);
    }

}
