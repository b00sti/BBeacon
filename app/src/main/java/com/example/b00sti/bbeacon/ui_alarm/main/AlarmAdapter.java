package com.example.b00sti.bbeacon.ui_alarm.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.ViewWrapper;
import com.example.b00sti.bbeacon.utils.SwitchButton;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EBean
public class AlarmAdapter extends BaseAdapter<AlarmItem, AlarmItemView> {

    @RootContext
    Context ctx;

    @Bean
    AlarmAdapterPresenter presenter;

    public void setView(AlarmContract.View view) {
        presenter.attachView(view);
    }

    @Override
    protected AlarmItemView onCreateItemView(ViewGroup parent, int viewType) {
        return AlarmItemView_.build(ctx);
    }

    @Override
    public void onBindViewHolder(final ViewWrapper<AlarmItemView> holder, final int position) {
        AlarmItemView alarmItemView = holder.getView();
        final AlarmItem alarmItem = dataSet.get(position);

        alarmItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onItemClick(alarmItem);
            }
        });

        alarmItemView.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                presenter.onSwitchClick(alarmItem, isChecked);
            }
        });

        alarmItemView.setOnDeleteClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRemoveClick(alarmItem, position);
            }
        });

        alarmItemView.bind(alarmItem);
    }
}
