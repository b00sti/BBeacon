package com.example.b00sti.bbeacon.ui_scanner;

import android.app.Activity;

import com.example.b00sti.bbeacon.base.BasePresenter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EBean
public class ScannerPresenter extends BasePresenter<ScannerContract.View> implements ScannerContract.Presenter {

    @RootContext
    Activity ctx;

    @Override
    public void fetchData() {
        addDisposable(new GetScannerInteractor().execute().subscribe(new Consumer<List<ScannerItem>>() {
            @Override
            public void accept(List<ScannerItem> items) throws Exception {
                view.refreshData(items);
            }
        }));
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }
}