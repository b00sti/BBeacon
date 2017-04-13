package com.example.b00sti.bbeacon.ui_scanner.top;

import android.support.v4.app.FragmentManager;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */
@EFragment(R.layout.scanner_top_fragment)
public class ScannerTopFragment extends BaseFragment<ScannerTopPresenter> implements ScannerTopContract.View, OnMapReadyCallback {
    private static final String TAG = "ScannerTopFragment";

    @Bean
    ScannerTopPresenter scannerTopPresenter;

    public static ScannerTopFragment newInstance() {
        return new ScannerTopFragment_();
    }

    @Override
    protected ScannerTopPresenter registerPresenter() {
        scannerTopPresenter.attachView(this);
        return scannerTopPresenter;
    }

    @AfterViews
    void init() {
        presenter.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        presenter.showDataOnTheMap(googleMap);
    }

    @Override
    public FragmentManager getViewChildFragmentManager() {
        return getChildFragmentManager();
    }
}