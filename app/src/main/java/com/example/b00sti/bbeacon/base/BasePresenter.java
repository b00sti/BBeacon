package com.example.b00sti.bbeacon.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Dominik (b00sti) Pawlik on 2016-11-14
 */

public abstract class BasePresenter<P> implements MvpPresenter {
    private static final String TAG = "BasePresenter";

    public P view;
    private CompositeDisposable compositeDisposable;

    public void attachView(P view) {
        this.view = view;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public abstract void onSubscribe();

    public abstract void onUnsubscribe();

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void subscribe() {
        compositeDisposable = new CompositeDisposable();
        onSubscribe();
    }

    @Override
    public void unsubscribe() {
        onUnsubscribe();
        compositeDisposable.dispose();
        view = null;
    }
}
