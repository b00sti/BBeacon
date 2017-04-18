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

    private RealmUtils() {
    }

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

    public static <E extends RealmObject> void Remove(final Class<E> clazz, final String fieldTitle, final long fieldValue) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final E first = realm.where(clazz).equalTo(fieldTitle, fieldValue).findFirst();
                if (first != null) {
                    first.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
            }
        });
    }

    public static <E extends RealmObject> E Find(Class<E> clazz, String fieldTitle, long fieldValue) {
        final Realm realm = Realm.getDefaultInstance();
        final E first = realm.where(clazz).equalTo(fieldTitle, fieldValue).findFirst();
        E result = null;
        if (first != null) {
            result = realm.copyFromRealm(first);
        }
        realm.close();
        return result;
    }

    public static <E extends RealmObject> E Find(Class<E> clazz, String fieldTitle, String fieldValue) {
        final Realm realm = Realm.getDefaultInstance();
        final E first = realm.where(clazz).equalTo(fieldTitle, fieldValue).findFirst();
        E result = null;
        if (first != null) {
            result = realm.copyFromRealm(first);
        }
        realm.close();
        return result;
    }

    public static <E extends RealmObject> List<E> FindAll(Class<E> clazz, String fieldTitle, String fieldValue) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<E> all = realm.where(clazz).equalTo(fieldTitle, fieldValue).findAll();
        final List<E> list = realm.copyFromRealm(all);
        realm.close();
        return list;
    }

    public static <E extends RealmObject> void SaveAll(final E item, @Nullable final OnSuccessListener onSuccessListener) {
        List<E> list = new ArrayList<>();
        list.add(item);
        RealmUtils.SaveAll(list, onSuccessListener);
    }

    public static <E extends RealmObject> void RemoveAll(final E item, @Nullable final OnSuccessListener onSuccessListener) {
        List<E> list = new ArrayList<>();
        list.add(item);
        RealmUtils.RemoveAll(list, onSuccessListener);
    }

    public static <E extends RealmObject> void RemoveAll(final List<E> items, @Nullable final OnSuccessListener onSuccessListener) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (E item : items) {
                    item.deleteFromRealm();
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

    public static <E extends RealmObject> void SaveAllWithId(E item, OnSuccessListener onSuccessListener) {
        List<E> list = new ArrayList<>();
        list.add(item);
        RealmUtils.SaveAllWithId(list, onSuccessListener);
    }

/*    public static <E extends RealmObject> void SaveAllWithId(final List<E> items, @Nullable final OnSuccessListener onSuccessListener) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void getFromRealm(Realm realm) {
                for (E item : items) {
                    long primaryKeyValue;
                    Number e = realm.where(item.getClass()).max("id");
                    if (e != null) {
                        primaryKeyValue = e.longValue();
                        primaryKeyValue = primaryKeyValue + 1;

                        if (item instanceof SettingId) {
                            ((SettingId) item).setManualId(primaryKeyValue);
                        }

                    }
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
    }*/

    public static <E extends RealmObject> void SaveAllWithId(final List<E> items, @Nullable final OnSuccessListener onSuccessListener) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (E item : items) {

                    //if item has a id copy or update
                    if (item instanceof SettingId) {
                        if (((SettingId) item).getManualId() != 0) {
                            realm.copyToRealmOrUpdate(item);
                            continue;
                        }
                    }

                    //get max id
                    RealmResults all = realm.where(item.getClass()).findAll();
                    Number e = null;
                    if (!all.isEmpty()) {
                        e = all.max("id");
                    }

                    //set id to max + 1 or to 1 if max is not available
                    long primaryKeyValue;
                    if (e != null) {
                        primaryKeyValue = e.longValue();
                        primaryKeyValue = primaryKeyValue + 1;
                    } else {
                        primaryKeyValue = 1;
                    }

                    if (item instanceof SettingId) {
                        ((SettingId) item).setManualId(primaryKeyValue);
                    }

                    //copy or update
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
