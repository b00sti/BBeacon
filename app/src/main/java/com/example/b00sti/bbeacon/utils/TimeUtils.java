package com.example.b00sti.bbeacon.utils;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-28
 */

public class TimeUtils {
    public static String getTimeWith0(String time) {
        String finalTime = "";
        String segments[] = time.split(":");
        for (int i = 0; i < segments.length; i++) {
            if (Integer.valueOf(segments[i]) < 10) {
                segments[i] = "0" + segments[i];
            }
        }

        finalTime = TimeUtils.join(Arrays.asList(segments), ":");
        return finalTime;
    }

    public static String join(Iterable<? extends Object> elements, CharSequence separator) {
        StringBuilder builder = new StringBuilder();

        if (elements != null) {
            Iterator<? extends Object> iter = elements.iterator();
            if (iter.hasNext()) {
                builder.append(String.valueOf(iter.next()));
                while (iter.hasNext()) {
                    builder
                            .append(separator)
                            .append(String.valueOf(iter.next()));
                }
            }
        }

        return builder.toString();
    }
}