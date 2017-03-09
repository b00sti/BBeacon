package com.example.b00sti.bbeacon.alarm;

import android.content.Context;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EBean
public class AlarmAdapter extends BaseAdapter<AlarmItem, AlarmItemView> {

    @RootContext
    Context context;

    @Override
    protected AlarmItemView onCreateItemView(ViewGroup parent, int viewType) {
        return AlarmItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<AlarmItemView> holder, int position) {
        AlarmItemView alarmItemView = holder.getView();
        AlarmItem alarmItem = dataSet.get(position);
        alarmItemView.bind(alarmItem);
    }
}
