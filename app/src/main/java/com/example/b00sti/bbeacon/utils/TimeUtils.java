package com.example.b00sti.bbeacon.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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
    public static String twoDatesBetweenTime(long oldtime) {
        int day;
        int hh;
        int mm;

        Date cDate = new Date();
        Long timeDiff = oldtime - cDate.getTime();
        day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
        hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day));
        mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        if(day==0) {
            return hh + " h " + mm + " min";
        }
        else if(hh==0) {
            return mm + " min";
        } else {
            return day + " message " + hh + " h " + mm + " min";
        }
    }

}
