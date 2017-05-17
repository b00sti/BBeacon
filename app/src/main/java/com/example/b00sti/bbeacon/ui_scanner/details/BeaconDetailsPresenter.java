package com.example.b00sti.bbeacon.ui_scanner.details;

import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;

import org.androidannotations.annotations.EBean;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-10
 */

@EBean
public class BeaconDetailsPresenter extends BasePresenter<BeaconDetailsContract.View> implements BeaconDetailsContract.Presenter {

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    @Override
    public void onPositiveClick(ScannerItem scannerItem) {

    }

    @Override
    public void onNegativeClick() {
        view.finishActivity();
    }

    @Override
    public void onMovingAlarmClick() {
        view.changeMovingAlarmView(false);
    }

    @Override
    public ScannerItem getActualScannerItem() {
        return null;
    }
}
