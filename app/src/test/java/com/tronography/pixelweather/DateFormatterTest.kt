package com.tronography.pixelweather

import android.text.format.DateUtils

import com.tronography.pixelweather.utils.DateFormatter

import org.junit.Assert
import org.junit.Test


class DateFormatterTest {

    @Test
    fun dateFormatterTest() {
        val dateToFormat: Long = 1508095680
        val expectedResult = "SUN\n10/15 3pm"

        Assert.assertEquals(expectedResult, DateFormatter.forecastDateFormatter(dateToFormat))
    }

    @Test
    fun hourlyFormatTest() {
        val dateToFormat: Long = 1508095680
        val expectedResult = "3pm"

        Assert.assertEquals(expectedResult, DateFormatter.hourlyFormat(dateToFormat))
    }

    @Test
    fun dayFormatTest() {
        val dateToFormat: Long = 1508095680
        val expectedResult = "SUN"

        Assert.assertEquals(expectedResult, DateFormatter.dayFormat(dateToFormat))
    }
}
