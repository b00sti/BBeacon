package com.example.b00sti.bbeacon.ui_scanner;

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
public class ScannerAdapter extends BaseAdapter<ScannerItem, ScannerItemView> {

    @RootContext
    Context context;

    @Override
    protected ScannerItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ScannerItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ScannerItemView> holder, int position) {
        ScannerItemView scannerItemView = holder.getView();
        ScannerItem scannerItem = dataSet.get(position);
        scannerItemView.bind(scannerItem);
    }
}
