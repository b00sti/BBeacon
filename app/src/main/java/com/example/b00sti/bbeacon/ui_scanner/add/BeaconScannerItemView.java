package com.example.b00sti.bbeacon.ui_scanner.add;

import android.content.Context;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.beacon_scanner_item_view)
public class BeaconScannerItemView extends BaseItemView<BeaconScannnerItem> {

    @ViewById(R.id.titleTV) TextView titleTV;
    @ViewById(R.id.uuidTV) TextView uuidTV;


    public BeaconScannerItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(BeaconScannnerItem beaconScannnerItem) {
        titleTV.setText(beaconScannnerItem.getName());
        uuidTV.setText(beaconScannnerItem.getUuid());
    }
}