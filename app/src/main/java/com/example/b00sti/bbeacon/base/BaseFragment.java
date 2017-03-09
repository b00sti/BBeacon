package com.example.b00sti.bbeacon.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.EFragment;

/**
 * Created by Dominik (b00sti) Pawlik on 2016-11-03
 */
@EFragment
public abstract class BaseFragment<P extends BasePresenter> extends BaseRefreshableFragment {
    private static final String TAG = "BaseFragment";

    public P presenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter == null) {
            presenter = registerPresenter();
            Log.d(TAG, "onViewCreated: " + presenter + " created");
        }
        presenter.subscribe();
        Log.d(TAG, "onViewCreated: " + presenter + " subscribed");
    }

    @Override
    public void onDestroyView() {
        presenter.unsubscribe();
        Log.d(TAG, "onDestroyView: unsubscribed");
        super.onDestroyView();
    }

    protected abstract P registerPresenter();
}
