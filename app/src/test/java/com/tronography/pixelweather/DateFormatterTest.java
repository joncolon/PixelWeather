package com.tronography.pixelweather;

import android.text.format.DateUtils;

import com.tronography.pixelweather.utils.DateFormatter;

import org.junit.Assert;
import org.junit.Test;


public class DateFormatterTest {

    @Test
    public void dateFormatterTest(){
        long dateToFormat = 1508095680;
        String expectedResult = "SUN\n10/15 3pm";

        Assert.assertEquals(expectedResult, DateFormatter.INSTANCE.forecastDateFormatter(dateToFormat));
    }
}
