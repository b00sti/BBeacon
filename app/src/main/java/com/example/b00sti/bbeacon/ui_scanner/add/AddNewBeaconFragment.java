package com.example.b00sti.bbeacon.ui_scanner.add;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.add_new_beacon_fragment)
public class AddNewBeaconFragment extends BaseFragment<AddNewBeaconPresenter> implements AddNewBeaconContract.View {

    private static final long SCAN_PERIOD = 10000;
    @ViewById(R.id.titleET) EditText titleET;
    @ViewById(R.id.rangeET) EditText rangeET;
    @ViewById(R.id.selectBeaconTV) AppCompatButton selectColorB;
    @ViewById(R.id.enabledCB) CheckBox enabledCB;
    @ViewById(R.id.saveB) AppCompatButton saveB;
    @ViewById(R.id.discoveredDevicesRV) RecyclerView discoveredDevicesRV;
    @Bean
    AddNewBeaconPresenter addNewBeaconPresenter;
    @Bean
    LeDeviceAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    getActivity().runOnUiThread(() -> {
                        Log.d("", "onLeScan: " + rssi);
                        BeaconScannnerItem beaconScannnerItem = new BeaconScannnerItem();
                        beaconScannnerItem.setName(device.getName());
                        beaconScannnerItem.setUuid(device.getAddress());
                        mLeDeviceListAdapter.addItem(beaconScannnerItem);
                        mLeDeviceListAdapter.notifyDataSetChanged();
                    });
                }
            };

    public static AddNewBeaconFragment newInstance() {
        return new AddNewBeaconFragment_();
    }

    @AfterViews
    void initBluetooth() {
        initBLE();
        startScan();
    }

    void initBLE() {
        mHandler = new Handler();
        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), "BLE not supported Here!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }

        discoveredDevicesRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        discoveredDevicesRV.setLayoutManager(layoutManager);
        discoveredDevicesRV.setAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }

    void startScan() {
        mLeDeviceListAdapter.setDataSet(new ArrayList<>());
        scanLeDevice(true);
    }

    void stopScan() {
        scanLeDevice(false);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
/*            mHandler.postDelayed(() -> {
                mScanning = false;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                //invalidateOptionsMenu();
            }, SCAN_PERIOD);

            mScanning = true;*/
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        //invalidateOptionsMenu();
    }

    @Click(R.id.selectBeaconTV)
    void selectColor() {
        presenter.selectColor();
    }

    @Click(R.id.saveB)
    void save() {
        presenter.storageNewItem();
    }

    @Override
    protected AddNewBeaconPresenter registerPresenter() {
        addNewBeaconPresenter.attachView(this);
        return addNewBeaconPresenter;
    }

    @Override
    public boolean getEnableDisable() {
        return enabledCB.isChecked();
    }

    @Override
    public String getRange() {
        return rangeET.getText().toString();
    }

    @Override
    public String getTitle() {
        return titleET.getText().toString();
    }
}
