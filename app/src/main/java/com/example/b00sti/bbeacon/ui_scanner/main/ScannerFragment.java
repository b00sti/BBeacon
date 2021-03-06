package com.example.b00sti.bbeacon.ui_scanner.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

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

@EFragment(R.layout.scanner_fragment)
public class ScannerFragment extends BaseFragment<ScannerPresenter> implements ScannerContract.View, RefreshableFragmentWithToolbar {

    @ViewById(R.id.fragment_container) FrameLayout fragmentContainer;

    @ViewById(R.id.mainRV) RecyclerView recyclerView;

    @Bean
    ScannerPresenter scannerPresenter;

    @Bean
    ScannerAdapter scannerAdapter;

    public static ScannerFragment newInstance() {
        return new ScannerFragment_();
    }

    @AfterViews
    void initUI() {
        initRecycler();
        presenter.fetchData();
    }

    private void initToolbar() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitleToToolbar(getString(R.string.tracked_beacons));
        }
    }

    @Override
    protected ScannerPresenter registerPresenter() {
        scannerPresenter.attachView(this);
        return scannerPresenter;
    }

    private void initRecycler() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        scannerAdapter.setDataSet(new ArrayList<ScannerItem>());
        recyclerView.setAdapter(scannerAdapter);
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
        initToolbar();
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    @Override
    public void refreshData(List<ScannerItem> items) {
        scannerAdapter.setDataSet(items);
        scannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshToolbar() {

    }
}
