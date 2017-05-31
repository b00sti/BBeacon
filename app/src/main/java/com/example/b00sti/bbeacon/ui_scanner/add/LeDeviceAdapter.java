package com.example.b00sti.bbeacon.ui_scanner.add;

import android.content.Context;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EBean
public class LeDeviceAdapter extends BaseAdapter<BeaconScannnerItem, BeaconScannerItemView> {

    @RootContext
    Context context;

    @Override
    protected BeaconScannerItemView onCreateItemView(ViewGroup parent, int viewType) {
        return BeaconScannerItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<BeaconScannerItemView> holder, int position) {
        BeaconScannerItemView scannerItemView = holder.getView();
        BeaconScannnerItem scannerItem = dataSet.get(position);
        scannerItemView.bind(scannerItem);
        //scannerItemView.setOnClickListener(v -> ActivityBuilder.startBeaconDetailsActivity(scannerItem, context));
    }
}
