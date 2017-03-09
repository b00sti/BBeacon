package com.example.b00sti.bbeacon.weather;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

@EFragment(R.layout.weather_fragment)
public class WeatherFragment extends BaseFragment<WeatherPresenter> implements WeatherContract.View {

    @ViewById(R.id.fragment_container) FrameLayout fragmentContainer;

    @ViewById(R.id.mainRV) RecyclerView recyclerView;

    @Bean
    WeatherPresenter presenter;

    @Bean
    WeatherAdapter weatherAdapter;

    public static WeatherFragment newInstance() {
        return new WeatherFragment_();
    }

    @Override
    protected WeatherPresenter registerPresenter() {
        presenter.attachView(this);
        return presenter;
    }

    @AfterViews
    void initUI() {
        initList();
    }

    private void initList() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        weatherAdapter.setDataSet(new ArrayList<WeatherItem>());
        recyclerView.setAdapter(weatherAdapter);
        presenter.fetchData();
    }

    @Override
    public void refresh() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
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
    public void refreshData(List<WeatherItem> items) {
        weatherAdapter.setDataSet(items);
        weatherAdapter.notifyDataSetChanged();
    }

}
