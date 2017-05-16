package com.example.b00sti.bbeacon.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-10
 */

public class BLEManager {

    private static final int REQUEST_ENABLE_BT = 199;
    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    Activity activity;
    BluetoothGatt bluetoothGatt;

    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            // this will get called anytime you perform a read or write characteristic operation
            byte[] data = characteristic.getValue();
        }

        @Override
        public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
            // this will get called when a device connects or disconnects
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
            // this will get called after the client initiates a 			BluetoothGatt.discoverServices() call
            bluetoothGatt.discoverServices();
            List<BluetoothGattService> services = bluetoothGatt.getServices();
            List<BluetoothGattCharacteristic> characteristics = null;
            for (BluetoothGattService service : services) {
                characteristics = service.getCharacteristics();
            }

            for (BluetoothGattCharacteristic characteristic : characteristics) {
                for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                    //find descriptor UUID that matches Client Characteristic Configuration (0x2902)
                    // and then call setValue on that descriptor

                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    bluetoothGatt.writeDescriptor(descriptor);
                }
            }
        }
    };
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            // your implementation here
            bluetoothGatt = device.connectGatt(activity, false, btleGattCallback);
        }
    };

    void init(Activity context) {
        this.activity = context;

        btManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    void startDevicesScan() {
        btAdapter.startLeScan(leScanCallback);
    }

    void stopDevicesScan() {
        btAdapter.stopLeScan(leScanCallback);
    }

    void finishGatt() {
        bluetoothGatt.disconnect();
        bluetoothGatt.close();
    }

}
