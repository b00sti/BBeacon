package com.example.b00sti.bbeacon.utils;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class RealmUtils {

    public static <E extends RealmObject> Observable<List<E>> FindAllAsync(Class<E> clazz) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<E> all = realm.where(clazz).findAll();
        final List<E> list = realm.copyFromRealm(all);

        return Observable.just(list)
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        realm.close();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <E extends RealmObject> List<E> FindAll(Class<E> clazz) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<E> all = realm.where(clazz).findAll();
        final List<E> list = realm.copyFromRealm(all);
        realm.close();
        return list;
    }

    public static <E extends RealmObject> void SaveAll(final E item, @Nullable final OnSuccessListener onSuccessListener) {
        List<E> list = new ArrayList<>();
        list.add(item);
        RealmUtils.SaveAll(list, onSuccessListener);
    }

    public static <E extends RealmObject> void SaveAll(final List<E> items, @Nullable final OnSuccessListener onSuccessListener) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (E item : items) {
                    realm.copyToRealmOrUpdate(item);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
                if (onSuccessListener != null) {
                    onSuccessListener.onSuccess();
                }
            }
        });
    }

    public interface OnSuccessListener {
        void onSuccess();
    }
}
