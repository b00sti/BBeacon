package com.example.b00sti.bbeacon.navigation;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-02-01
 */

@EFragment
public abstract class BaseRefreshableFragment extends Fragment {
    public abstract void refresh();

    public abstract void willBeHidden();

    public abstract void willBeDisplayed();
}
