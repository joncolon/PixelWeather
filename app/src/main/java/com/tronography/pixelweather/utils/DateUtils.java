package com.tronography.pixelweather.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateUtils {

    public static String dateFormatter(long timeInMilli) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(timeInMilli * 1000);
        int month = (calendar.get(Calendar.MONTH)) + 1;
        int dayOfMonth = (calendar.get(Calendar.DAY_OF_MONTH));
        int hourOfDay = (calendar.get(Calendar.HOUR));
        int isAMorPM = (calendar.get(Calendar.AM_PM));

        String hour = hourOfDay == 0 ? "12" : String.valueOf(hourOfDay);

        String amPm = isAMorPM == 0 ? "am" : "pm";

        return new StringBuilder()
                .append(month)
                .append("/")
                .append(dayOfMonth)
                .append("\n")
                .append(hour)
                .append(amPm).toString();
    }
}
