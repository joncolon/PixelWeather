package com.tronography.pixelweather.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {

    public static String forecastDateFormatter(long timeInMilli) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(timeInMilli * 1000);
        int month = (calendar.get(Calendar.MONTH)) + 1;
        int dayOfMonth = (calendar.get(Calendar.DAY_OF_MONTH));
        int hourOfDay = (calendar.get(Calendar.HOUR));
        int isAMorPM = (calendar.get(Calendar.AM_PM));

        String hour = hourOfDay == 0 ? "12" : String.valueOf(hourOfDay);

        String amPm = isAMorPM == 0 ? "am" : "pm";

        return new StringBuilder()
                .append(dayStringFormat(timeInMilli))
                .append("\n")
                .append(month)
                .append("/")
                .append(dayOfMonth)
                .append(" ")
                .append(hour)
                .append(amPm).toString();
    }

    // convert milliseconds into the day of the week string
    public static String dayStringFormat(long msecs) {
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date(msecs * 1000));

        int dow = cal.get(Calendar.DAY_OF_WEEK);

        switch (dow) {
            case Calendar.MONDAY:
                return "MON";
            case Calendar.TUESDAY:
                return "TUE";
            case Calendar.WEDNESDAY:
                return "WED";
            case Calendar.THURSDAY:
                return "THU";
            case Calendar.FRIDAY:
                return "FRI";
            case Calendar.SATURDAY:
                return "SAT";
            case Calendar.SUNDAY:
                return "SUN";
        }

        return "Unknown";
    }
}
