package com.example.b00sti.bbeacon.bluetooth;

/**
 * Created by b00sti on 16.01.2017
 */

public class BluetoothBundle {
    public final String charset;
    public final long bluetoothSleep;
    public final int bufferCapacity;

    public BluetoothBundle(String charset, long bluetoothSleep, int bufferCapacity) {
        this.charset = charset;
        this.bluetoothSleep = bluetoothSleep;
        this.bufferCapacity = bufferCapacity;
    }
}
