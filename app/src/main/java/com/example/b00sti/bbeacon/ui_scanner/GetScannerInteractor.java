package com.example.b00sti.bbeacon.ui_scanner;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetScannerInteractor {

    public Observable<List<ScannerItem>> execute() {
        boolean fakeData = false;

        if (fakeData) {

            //fake time
            List<ScannerItem> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add(new ScannerItem("Beacon : " + i, 111111, true, "kiedys", 33));
            }

            return Observable.just(itemsData)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());

        } else {

            //right time
            return RealmUtils.FindAllAsync(ScannerItem.class);
        }

    }
}
