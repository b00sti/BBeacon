package com.example.b00sti.bbeacon.ui_scanner;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-15
 */

public class SetScannerInteractor {

    public void execute(List<ScannerItem> data) {
        RealmUtils.SaveAll(data, null);
    }

    public void execute(ScannerItem scannerItem) {
        List<ScannerItem> data = new ArrayList<>();
        data.add(scannerItem);
        RealmUtils.SaveAll(data, null);
    }

    public void execute(ScannerItem scannerItem, RealmUtils.OnSuccessListener listener) {
        List<ScannerItem> data = new ArrayList<>();
        data.add(scannerItem);
        RealmUtils.SaveAll(data, listener);
    }

    public void execute(List<ScannerItem> data, RealmUtils.OnSuccessListener listener) {
        RealmUtils.SaveAll(data, listener);
    }

}
