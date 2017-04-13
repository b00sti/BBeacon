package com.example.b00sti.bbeacon.ui_scanner.interactors;

import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetScannerInteractor {

    public Observable<List<ScannerItem>> execute() {
        return RealmUtils.FindAllAsync(ScannerItem.class);
    }
}
