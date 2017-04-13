package com.example.b00sti.bbeacon.ui_alarm.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.b00sti.bbeacon.MainActivity;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.base.RefreshableFragmentWithToolbar;

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
public class AlarmFragment extends BaseFragment<AlarmPresenter> implements AlarmContract.View, RefreshableFragmentWithToolbar {
    private static final String TAG = "AlarmFragment";

    @ViewById(R.id.fragment_container) FrameLayout fragmentContainer;
    @ViewById(R.id.mainRV) RecyclerView recyclerView;
    @ViewById(R.id.noAvailableTV) TextView noAvailableTV;

    @Bean
    AlarmPresenter alarmPresenter;

    @Bean
    AlarmAdapter alarmAdapter;

    public static AlarmFragment newInstance() {
        return new AlarmFragment_();
    }

    @AfterViews
    void initUI() {
        initList();
        refreshToolbar();
    }

    @Override
    protected AlarmPresenter registerPresenter() {
        alarmPresenter.attachView(this);
        return alarmPresenter;
    }

    private void initList() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        alarmAdapter.setDataSet(new ArrayList<AlarmItem>());
        alarmAdapter.setView(this);
        recyclerView.setAdapter(alarmAdapter);
        presenter.fetchData();
    }

    @Override
    public void refresh() {
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
        refreshToolbar();
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    @Override
    public void refreshData(List<AlarmItem> items) {
        if (items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noAvailableTV.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noAvailableTV.setVisibility(View.GONE);
            alarmAdapter.setDataSet(items);
        }
    }

    @Override
    public AlarmAdapter getAdapter() {
        return alarmAdapter;
    }

    @Override
    public void refreshToolbar() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitleToToolbar(presenter.getTitleToRefreshToolbar());
        }
    }

    @Override
    public void onEmptyDataSet(boolean isEmpty) {
        if (isEmpty) {
            recyclerView.setVisibility(View.GONE);
            noAvailableTV.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noAvailableTV.setVisibility(View.GONE);
        }
    }
}
