package com.example.b00sti.bbeacon.utils;

import java.util.Locale;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-26
 */

public class NumUtils {
    private NumUtils() {
    }

    public static double toD(int i) {
        return (double) i;
    }

    public static String toS(double i) {
        return String.format(Locale.getDefault(), "%.1f", i);
    }

}
