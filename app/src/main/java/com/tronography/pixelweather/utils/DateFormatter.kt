package com.tronography.pixelweather.utils

import android.text.format.DateUtils.isToday
import java.util.*
import java.util.Calendar.*


object DateFormatter {
    private val calendar: Calendar = GregorianCalendar.getInstance()
    private const val AM: String = "am"
    private const val PM: String = "pm"
    private const val TWELVE: String = "12"

    private val hourOfDayInTwelveHourClock: String
        get() {
            val hourOfDay: Int = calendar.get(HOUR)
            if (hourOfDay.equals(0)) return TWELVE else return hourOfDay.toString()
        }

    private val aMorPM: String
        get() {
            val isAMorPM = calendar.get(AM_PM)
            if (isAMorPM.equals(0)) return AM else return PM
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
