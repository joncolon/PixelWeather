package com.tronography.pixelweather;

import com.tronography.pixelweather.utils.IconUrlUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IconUrlUtilsTest {

    @Test
    public void getIconUrlTest(){
        String icon = "02d";
        String expectedResult = "http://openweathermap.org/img/w/02d.png";

        assertEquals(expectedResult, IconUrlUtils.Companion.getIconUrl(icon));
    }
}
