package com.example.sean.trackmyjob

import com.example.sean.trackmyjob.Business.TimeCalculator
import com.example.sean.trackmyjob.Models.TimeDiff
import java.time.LocalDateTime
import org.junit.Test
import org.junit.Assert.*

class TimeCalculatorUnitTest {

    @Test
    fun difference_isCorrect()
    {
        val dateStart = LocalDateTime.of(2018, 12, 1, 0,0,0)
        val dateEnd = LocalDateTime.of(2018, 12, 1, 8,10,0)
        val diff = TimeCalculator.difference(dateStart,dateEnd)

        assertEquals(8, diff.Hours)
        assertEquals(10, diff.Minutes)
    }

    @Test
    fun difference_isNegative()
    {
        val dateOne = LocalDateTime.of(2018, 12, 1, 8,10,0)
        val dateSecond = LocalDateTime.of(2018, 12, 1, 0,0,0)
        val diff = TimeCalculator.difference(dateOne,dateSecond)

        assertEquals(-8, diff.Hours)
        assertEquals(-10, diff.Minutes)
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Combination

    @Test
    fun combineTimeDiffs_isCorrect()
    {
        val timeOne = TimeDiff(1, 1)
        val timeTwo = TimeDiff(1, 1)

        val diff = TimeCalculator.combineTimeDiffs(timeOne, timeTwo)

        assertEquals(2, diff.Hours)
        assertEquals(2, diff.Minutes)
    }

    @Test
    fun combineTimeDiffs_combineMinutesOverSixty()
    {
        val timeOne = TimeDiff(1, 31)
        val timeTwo = TimeDiff(1, 31)

        val diff = TimeCalculator.combineTimeDiffs(timeOne, timeTwo)

        assertEquals(3, diff.Hours)
        assertEquals(2, diff.Minutes)
    }
}