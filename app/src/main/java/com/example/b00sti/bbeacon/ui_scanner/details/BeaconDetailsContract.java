package com.example.b00sti.bbeacon.ui_scanner.details;

import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-10
 */

interface BeaconDetailsContract {

    interface View {

        void changeMovingAlarmView(boolean isAlarmed);

        void finishActivity();
    }

    interface Presenter {

        void onPositiveClick(ScannerItem scannerItem);

        void onNegativeClick();

        void onMovingAlarmClick();

        ScannerItem getActualScannerItem();
    }
}