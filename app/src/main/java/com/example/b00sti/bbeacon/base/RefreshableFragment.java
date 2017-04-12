package com.example.b00sti.bbeacon.base;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-12
 */

public interface RefreshableFragment {
    void refresh();

    void willBeHidden();

    void willBeDisplayed();
}
