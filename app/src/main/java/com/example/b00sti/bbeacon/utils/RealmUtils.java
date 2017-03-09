package com.example.b00sti.bbeacon.utils;

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
}
