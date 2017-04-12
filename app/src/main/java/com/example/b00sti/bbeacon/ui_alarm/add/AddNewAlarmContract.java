package com.example.b00sti.bbeacon.ui_alarm.add;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-12
 */

public class AddNewAlarmContract {

    public interface View {

        void setTime();

        void setTimeToNextAlarm();

    }

    public interface Presenter {

        void selectTime();

        void setTitle();

        void selectBeacon();

        void enableDisable(boolean newStatus);

        void onCancel();

        void onAccept();
    }


}
