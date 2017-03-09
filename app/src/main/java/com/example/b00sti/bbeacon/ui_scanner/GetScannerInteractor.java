package com.example.b00sti.bbeacon.ui_scanner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetScannerInteractor {

    public Observable<List<ScannerItem>> execute() {
        //fake data
        List<ScannerItem> itemsData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemsData.add(new ScannerItem("Fragment Scanner as mvp - " + " Item : " + i));
        }

        return Observable.just(itemsData)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

        //return RealmUtils.FindAllAsync(ScannerItem.class);
    }
}
