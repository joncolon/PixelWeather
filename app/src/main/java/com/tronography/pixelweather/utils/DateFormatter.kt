package com.tronography.pixelweather.utils

import java.util.GregorianCalendar

import android.text.format.DateUtils.isToday
import java.util.Calendar.*


object DateFormatter {
    private val calendar = GregorianCalendar.getInstance()

    private val hourOfDayInTwelveHourClock: String
        get() {
            val hourOfDay = calendar.get(HOUR)
            return if (hourOfDay == 0) "12" else hourOfDay.toString()
        }

    private val aMorPM: String
        get() {
            val isAMorPM = calendar.get(AM_PM)
            return if (isAMorPM == 0) "am" else "pm"
        }

    private val dayOfMonth: Int
        get() = calendar.get(DAY_OF_MONTH)

    private val month: Int
        get() = calendar.get(MONTH) + 1

    private val nameOfDay: Int
        get() = calendar.get(DAY_OF_WEEK)

    fun forecastDateFormatter(timeInMilli: Long): String {
        calendar.timeInMillis = timeInMilli * 1000

        return StringBuilder()
                .append(getNameOfDay(timeInMilli))
                .append("\n")
                .append(month)
                .append("/")
                .append(dayOfMonth)
                .append(" ")
                .append(hourOfDayInTwelveHourClock)
                .append(aMorPM)
                .toString()
    }

    private fun getNameOfDay(timeInMilliseconds: Long): String {

        if (isToday(timeInMilliseconds * 1000)) {
            return "TODAY"
        }

        when (nameOfDay) {
            MONDAY -> return "MON"
            TUESDAY -> return "TUE"
            WEDNESDAY -> return "WED"
            THURSDAY -> return "THU"
            FRIDAY -> return "FRI"
            SATURDAY -> return "SAT"
            SUNDAY -> return "SUN"
        }

        return "Unknown"
    }
}
