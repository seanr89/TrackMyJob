package com.example.sean.trackmyjob

import com.example.sean.trackmyjob.Business.HoursAndMinutes
import org.junit.Test
import org.junit.Assert.*

class HoursAndMinutesUnitTest
{
    @Test
    fun calcHours_isLessThanSixty()
    {
        val calc = HoursAndMinutes(59)
        assertEquals(0, calc.calculateHours())
    }

    @Test
    fun calcHours_isSixty()
    {
        val calc = HoursAndMinutes(60)
        assertEquals(1, calc.calculateHours())
    }

    @Test
    fun calcHours_isOneTwently()
    {
        val calc = HoursAndMinutes(120)
        assertEquals(2, calc.calculateHours())
    }

    @Test
    fun calcHours_isOverThree()
    {
        val calc = HoursAndMinutes(187)
        assertEquals(3, calc.calculateHours())
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Minutes Check

    @Test
    fun calcMinutes_isZero()
    {
        val calc = HoursAndMinutes(0)
        assertEquals(0, calc.calculateRemainingMinutesFromHours())
    }

    @Test
    fun calcMinutes_isUnderSixty()
    {
        val calc = HoursAndMinutes(59)
        assertEquals(59, calc.calculateRemainingMinutesFromHours())
    }

    @Test
    fun calcMinutes_isSixty()
    {
        val calc = HoursAndMinutes(60)
        assertEquals(0, calc.calculateRemainingMinutesFromHours())
    }

    @Test
    fun calcMinutes_isOverSixty()
    {
        val calc = HoursAndMinutes(137)
        assertEquals(17, calc.calculateRemainingMinutesFromHours())
    }
}