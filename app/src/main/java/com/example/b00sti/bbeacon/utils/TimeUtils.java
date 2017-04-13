package com.example.b00sti.bbeacon.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-28
 */

public class TimeUtils {

    public static final String DATE_FORMAT_MERGED = "yyyyMMdd";
    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String MONTH_DAY_FORMAT = "MMM d";
    public static final String SIMPLE_HOUR_MINUTE = "HH:mm";
    public static final String REGULAR_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    private static final String TAG = "TimeUtils";
    public static long MILLIS_IN_DAY = 86400000L;

    private TimeUtils() {
    }

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

    public static long getCurrentTimeMillis() {
        Calendar currentTime = Calendar.getInstance();
        long time = currentTime.getTimeInMillis() / 1000;
        return time * 1000;
    }

    public static int[] getTimeFromMillisInInt(long millis) {

        int[] result = new int[4];
        long seconds = millis / 1000 % 60;
        long minutes = millis / (60 * 1000) % 60;
        long hours = millis / (60 * 60 * 1000) % 24;
        long days = millis / (24 * 60 * 60 * 1000);
        // SECONDS
        result[0] = (int) seconds;
        // MINUTES
        result[1] = (int) minutes;
        // HOURS
        result[2] = (int) hours;
        // DAYS
        result[3] = (int) days;
        return result;
    }

    public static String getMonthDayFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(MONTH_DAY_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getStringFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_MERGED, Locale.getDefault());
        return format.format(date);
    }

    public static String getCurrentTimeInIsoFormat() {
        return getTimeInIsoFormat(new Date());
    }

    public static String getTimeInIsoFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ISO, Locale.getDefault());
        return sdf.format(date);
    }

    public static String getCurrentReadableDateAndTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(REGULAR_DATE_TIME_FORMAT, Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static String formatToReadableDateAndTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(REGULAR_DATE_TIME_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getCurrentTimeHourMinute(int secondsOffset) {
        String result = "0:00";
        Date now = new Date();
        long currentSeconds = (now.getTime() / 1000) + secondsOffset;
        Date newDate = new Date(currentSeconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_HOUR_MINUTE, Locale.getDefault());
        result = sdf.format(newDate);
        return result;
    }

    /**
     * Get hour string depending on user system preferences
     *
     * @return - current temp in proper format eg. 1, 9,
     */
    public static String getUserVisibleHour(Context context, int hour) {
        hour = hour % 24;
        if (android.text.format.DateFormat.is24HourFormat(context)) {
            return String.format(Locale.US, "%02d", hour);
        } else {
            //it is pm if hours from 12 onwards
            String suffix = (hour >= 12) ? "PM" : "AM";

            //only -12 from hours if it is greater than 12 (if not back at mid night)
            hour = (hour > 12) ? hour - 12 : hour;

            //if 0 then it is 12 am
            hour = (hour == 0) ? 12 : hour;

            return String.format(Locale.US, "%02d %s", hour, suffix);
        }
    }

    /**
     * Get temp string depending on user system preferences
     *
     * @return - current temp in 24h or AM/PM format
     */
    public static String getUserVisibleTime(Context context, Date date) {
        return android.text.format.DateFormat.getTimeFormat(context).format(date);
    }

    /**
     * Get date string depending on user system preferences
     *
     * @return - current temp in proper format eg. dd-mm-yyyy, mm-dd-yyy, yyyy-mm-dd
     */
    public static String getUserVisibleDate(Context context, Date date) {
        return android.text.format.DateFormat.getDateFormat(context).format(date).replace("/", "-");
    }

    public static String getCustomDateAndTimeFromMsForUser(Context context, long milliseconds) {
        Date date = new Date(milliseconds);
        return getUserVisibleTime(context, date) + ", " + getUserVisibleDate(context, date);
    }

    public static String getFormattedDateFromTimeInIso(String fullIsoTime) {
        if (fullIsoTime != null) return fullIsoTime.substring(0, 16).replaceAll("T", ", ");
        else return null;
    }

    public static String getOnlyDateFromTimeInIso(String fullIsoTime) {
        if (fullIsoTime != null) return fullIsoTime.substring(0, 10);
        else return null;
    }
}
