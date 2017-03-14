package com.example.b00sti.bbeacon.ui_scanner;

import android.support.v4.app.Fragment;

import com.example.b00sti.bbeacon.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */


@EFragment(R.layout.scanner_top_fragment)
public class ScannerTopFragment extends Fragment {

    public static ScannerTopFragment newInstance() {
        return new ScannerTopFragment_();
    }

}