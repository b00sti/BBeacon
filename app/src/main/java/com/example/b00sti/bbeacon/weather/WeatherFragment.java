package com.example.b00sti.bbeacon.weather;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseRefreshableFragment;
import com.example.b00sti.bbeacon.navigation.DemoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-08
 */

@EFragment(R.layout.weather_fragment)
public class WeatherFragment extends BaseRefreshableFragment {

    @ViewById(R.id.fragment_container) FrameLayout fragmentContainer;

    @ViewById(R.id.mainRV) RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    public static WeatherFragment newInstance() {
        return new WeatherFragment_();
    }

    @AfterViews
    void initUI() {
        initDemoList();
    }

    private void initDemoList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> itemsData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemsData.add("Fragment Weather" + " Item : " + i);
        }

        DemoAdapter adapter = new DemoAdapter(itemsData);
        recyclerView.setAdapter(adapter);
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
}
