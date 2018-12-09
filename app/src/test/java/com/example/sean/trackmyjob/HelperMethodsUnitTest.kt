package com.example.sean.trackmyjob

import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.time.LocalDateTime
import org.junit.Test
import org.junit.Assert.*

class HelperMethodsUnitTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Morning Checks

    @Test
    fun isMorning_morningCheck()
    {
        val date = LocalDateTime.of(0,1,1,11,59,0)
        assertEquals(true, HelperMethods.isMorning(date))
    }

    @Test
    fun isMorning_afternoonCheck()
    {
        var date = LocalDateTime.of(0,1,1,12,1,0)
        assertEquals(false, HelperMethods.isMorning(date))
    }

    @Test
    fun isMorning_noonCheck()
    {
        var date = LocalDateTime.of(0,1,1,12,0,0)
        assertEquals(false, HelperMethods.isMorning(date))
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Weekend Checks

    @Test
    fun isWeekend_fridayCheck()
    {
        val date = LocalDateTime.of(2018,1,5,0,0,0)
        assertEquals(false, HelperMethods.isWeekend(date))
    }

    @Test
    fun isWeekend_saturdayCheck()
    {
        val date = LocalDateTime.of(2018,1,6,0,0,0)
        assertEquals(true, HelperMethods.isWeekend(date))
    }

    @Test
    fun isWeekend_sundayCheck()
    {
        val date = LocalDateTime.of(2018,1,7,0,0,0)
        assertEquals(true, HelperMethods.isWeekend(date))
    }


    @Test
    fun isWeekend_mondayCheck()
    {
        val date = LocalDateTime.of(2018,1,8,0,0,0)
        assertEquals(false, HelperMethods.isWeekend(date))
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //String Checks (DateTime)


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Start of Week Check


    @Test
    fun startOfWeek_isFirst()
    {
        val date = LocalDateTime.of(2018,1,8,0,0,0)
        assertEquals(true, HelperMethods.isDayStartOfWeek(date))
    }

    @Test
    fun startOfWeek_isNotFirst()
    {
        val date = LocalDateTime.of(2018,1,9,0,0,0)
        assertEquals(false, HelperMethods.isDayStartOfWeek(date))
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Start of Month Check


    @Test
    fun startOfMonth_isFirst()
    {
        val date = LocalDateTime.of(2018,1,1,0,0,0)
        assertEquals(true, HelperMethods.isDayStartOfMonth(date))
    }

    @Test
    fun startOfMonth_isNotFirst()
    {
        val date = LocalDateTime.of(2018,1,2,0,0,0)
        assertEquals(false, HelperMethods.isDayStartOfMonth(date))
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Day Matcher

    @Test
    fun dayMatch_isCorrect()
    {
        val dateOne = LocalDateTime.of(2018,1,2,0,0,0)
        val dateTwo = LocalDateTime.of(2018,1,2,0,0,0)

        assertEquals(true, HelperMethods.doDaysMatch(dateOne,dateTwo))
    }

    @Test
    fun dayMatch_isInCorrect()
    {
        val dateOne = LocalDateTime.of(2018,1,1,0,0,0)
        val dateTwo = LocalDateTime.of(2018,1,2,0,0,0)

        assertEquals(false, HelperMethods.doDaysMatch(dateOne,dateTwo))
    }

}