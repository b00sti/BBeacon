package com.example.bskeleton.basics;

import android.util.Log;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2016-11-15
 */

public class CLog {

    public static <T> void logListSize(String TAG, String title, List<T> list) {
        if (list == null) {
            Log.e(TAG, title + ": list is null!");
        } else {
            Log.i(TAG, title + ": list size = [" + list.size() + "]");
        }
    }

    public static <T> void logList(String TAG, String title, List<T> list) {
        if (list == null) {
            Log.e(TAG, title + ": list is null!");
        } else {
            int i = 0;
            for (T t : list) {
                Log.i(TAG, "item " + i + " = [" + t.toString() + "]");
                i++;
            }
        }
    }

    //translation visibility of views to friendly format
    public static String getFriendlyVisibility(int visibility) {
        switch (visibility) {
            case 0:
                return "VISIBLE";
            case 4:
                return "INVISIBLE";
            case 8:
                return "GONE";
            default:
                return "NONE";
        }
    }

    public static <T> void d(String TAG, String info, T t) {
        String s1 = null;
        if (t != null) s1 = t.toString();
        Log.d(TAG, info + " = [" + s1 + "]");
    }

    public static <T, S> void d(String TAG, String info, T t, String info2, S s) {
        String s1 = null;
        String s2 = null;
        if (t != null) s1 = t.toString();
        if (s != null) s2 = s.toString();
        Log.d(TAG, info + " = [" + s1 + "], " + info2 + " = [" + s2 + "]");
    }

    public static <T, S, W> void d(String TAG, String info, T t, String info2, S s, String info3, W w) {
        String s1 = null;
        String s2 = null;
        String s3 = null;
        if (t != null) s1 = t.toString();
        if (s != null) s2 = s.toString();
        if (w != null) s3 = w.toString();
        Log.d(TAG, info + " = [" + s1 + "], " + info2 + " = [" + s2 + "], " + info3 + " = [" + s3 + "]");
    }

    public static <T> void i(String TAG, String info, T t) {
        String s1 = null;
        if (t != null) s1 = t.toString();
        Log.i(TAG, info + " = [" + s1 + "]");
    }

    public static <T, S> void i(String TAG, String info, T t, String info2, S s) {
        String s1 = null;
        String s2 = null;
        if (t != null) s1 = t.toString();
        if (s != null) s2 = s.toString();
        Log.i(TAG, info + " = [" + s1 + "], " + info2 + " = [" + s2 + "]");
    }

    public static <T, S, W> void i(String TAG, String info, T t, String info2, S s, String info3, W w) {
        String s1 = null;
        String s2 = null;
        String s3 = null;
        if (t != null) s1 = t.toString();
        if (s != null) s2 = s.toString();
        if (w != null) s3 = w.toString();
        Log.i(TAG, info + " = [" + s1 + "], " + info2 + " = [" + s2 + "], " + info3 + " = [" + s3 + "]");
    }

}

