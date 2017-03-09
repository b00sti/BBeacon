package com.example.b00sti.bbeacon.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-07
 */

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {
    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}
