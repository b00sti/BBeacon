package com.example.b00sti.bbeacon.base;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public abstract class BaseItemView<P> extends LinearLayout {

    public BaseItemView(Context context) {
        super(context);
    }

    public abstract void bind(P p);
}
