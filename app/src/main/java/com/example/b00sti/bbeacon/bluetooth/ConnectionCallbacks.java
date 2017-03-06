package com.example.b00sti.bbeacon.bluetooth;

/**
 * Created by b00sti on 16.01.2017
 */

public interface ConnectionCallbacks {

    void onConnected();

    void onDisconnected();

    void onError(String errorMessage);
}
