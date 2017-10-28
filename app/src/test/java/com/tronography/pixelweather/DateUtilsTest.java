package com.tronography.pixelweather;

import com.tronography.pixelweather.utils.DateUtils;

import org.junit.Assert;
import org.junit.Test;



public class DateUtilsTest {

    @Test
    public void dateFormatterTest(){
        long dateToFormat = 1508095680;
        String expectedResult = "10/15\n3pm";

        Assert.assertEquals(expectedResult, DateUtils.forecastDateFormatter(dateToFormat));
    }

}
