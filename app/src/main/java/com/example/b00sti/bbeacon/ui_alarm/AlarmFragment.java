package com.example.b00sti.bbeacon.ui_alarm;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-08
 */

@EFragment(R.layout.alarm_fragment)
public class AlarmFragment extends BaseFragment<AlarmPresenter> implements AlarmContract.View {
    private static final String TAG = "AlarmFragment";

    @ViewById(R.id.fragment_container) FrameLayout fragmentContainer;

    @ViewById(R.id.mainRV) RecyclerView recyclerView;

    @Bean
    AlarmPresenter presenter;

    @Bean
    AlarmAdapter alarmAdapter;

    public static AlarmFragment newInstance() {
        return new AlarmFragment_();
    }

    @AfterViews
    void initUI() {
        initList();
    }


    @Override
    protected AlarmPresenter registerPresenter() {
        presenter.attachView(this);
        return presenter;
    }

    private void initList() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        alarmAdapter.setDataSet(new ArrayList<AlarmItem>());
        recyclerView.setAdapter(alarmAdapter);
        presenter.fetchData();
    }

    @Override
    public void refresh() {
        Log.d(TAG, "refresh: " + recyclerView);
        if (recyclerView != null) {
            presenter.fetchData();
        }
    }

    @Override
    public void willBeHidden() {
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }

    @Override
    public void willBeDisplayed() {
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public void refreshData(List<AlarmItem> items) {
        alarmAdapter.setDataSet(items);
        alarmAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(0);
    }

}
