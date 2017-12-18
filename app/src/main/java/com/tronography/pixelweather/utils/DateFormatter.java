package com.tronography.pixelweather.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static android.text.format.DateUtils.isToday;
import static java.util.Calendar.*;


public class DateFormatter {
    private static Calendar calendar = GregorianCalendar.getInstance();

    public static String forecastDateFormatter(long timeInMilli) {
        calendar.setTimeInMillis(timeInMilli * 1000);

        return new StringBuilder()
                .append(getNameOfDay(timeInMilli))
                .append("\n")
                .append(getMonth())
                .append("/")
                .append(getDayOfMonth())
                .append(" ")
                .append(getHourOfDayInTwelveHourClock())
                .append(getAMorPM())
                .toString();
    }

    private static String getHourOfDayInTwelveHourClock() {
        int hourOfDay = (calendar.get(HOUR));
        String twelveHourClockHour = hourOfDay == 0 ? "12" : String.valueOf(hourOfDay);
        return twelveHourClockHour;
    }

    private static String getAMorPM() {
        int isAMorPM = (calendar.get(AM_PM));
        String amPm = isAMorPM == 0 ? "am" : "pm";
        return amPm;
    }

    private static int getDayOfMonth() {
        return (calendar.get(DAY_OF_MONTH));
    }

    private static int getMonth() {
        return (calendar.get(MONTH)) + 1;
    }

    private static String getNameOfDay(long timeInMilliseconds) {

        if (isToday(timeInMilliseconds * 1000)){
            return "TODAY";
        }

        switch (getNameOfDay()) {
            case MONDAY:
                return "MON";
            case TUESDAY:
                return "TUE";
            case WEDNESDAY:
                return "WED";
            case THURSDAY:
                return "THU";
            case FRIDAY:
                return "FRI";
            case SATURDAY:
                return "SAT";
            case SUNDAY:
                return "SUN";
        }

        return "Unknown";
    }

    private static int getNameOfDay() {
        return calendar.get(DAY_OF_WEEK);
    }
}
