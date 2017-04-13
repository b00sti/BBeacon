package com.example.b00sti.bbeacon.ui_weather.main;

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

@EFragment(R.layout.weather_fragment)
public class WeatherFragment extends BaseFragment<WeatherPresenter> implements WeatherContract.View, RefreshableFragmentWithToolbar {
    private static final String TAG = "WeatherFragment";

    @ViewById(R.id.fragment_container) FrameLayout fragmentContainer;
    @ViewById(R.id.noAvailableTV) TextView noAvailableTV;
    @ViewById(R.id.mainRV) RecyclerView recyclerView;

    @Bean
    WeatherPresenter weatherPresenter;

    @Bean
    WeatherAdapter weatherAdapter;

    public static WeatherFragment newInstance() {
        return new WeatherFragment_();
    }

    @Override
    protected WeatherPresenter registerPresenter() {
        weatherPresenter.attachView(this);
        return weatherPresenter;
    }

    @AfterViews
    void initUI() {
        initList();
    }

    private void initList() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setItemPrefetchEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        weatherAdapter.setDataSet(new ArrayList<WeatherItem>());
        recyclerView.setAdapter(weatherAdapter);
        presenter.fetchData();
    }

    public void refreshToolbar() {
        refreshToolbar("");
    }

    public void refreshToolbar(String title) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitleToToolbar(title);
        }
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
        refresh();
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }


    @Override
    public void refreshData(List<WeatherItem> items) {
        if (items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noAvailableTV.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noAvailableTV.setVisibility(View.GONE);
            weatherAdapter.setDataSet(items);
        }
    }

}
