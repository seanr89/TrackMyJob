package com.example.sean.trackmyjob

import com.example.sean.trackmyjob.Business.ClockEventStatsManager
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Models.Enums.LastClock
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class ClockEventStatsManagerUnitTest {

    @Test
    fun clockType_isCorrectSameDay()
    {
        val date = LocalDateTime.of(2018, 12,1, 23,59)
        val dateTwo = LocalDateTime.of(2018, 12,1, 0,0)
        val clockEvent = ClockEvent(ClockEventType.IN, dateTwo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        val clockStatsManager = ClockEventStatsManager()

        val outPut = clockStatsManager.getLastClockLastClockType(date, clockEvent)

        Assert.assertEquals(LastClock.SAME_DAY, outPut)

    }

    @Test
    fun clockType_isCorrectNewDay()
    {
        val date = LocalDateTime.of(2018, 12,4, 0,0)
        val dateTwo = LocalDateTime.of(2018, 12,3, 0,0)
        val clockEvent = ClockEvent(ClockEventType.IN, dateTwo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        val clockStatsManager = ClockEventStatsManager()

        val outPut = clockStatsManager.getLastClockLastClockType(date, clockEvent)

        Assert.assertEquals(LastClock.NEW_DAY, outPut)
    }

    @Test
    fun clockType_isCorrectNewWeek()
    {
        val date = LocalDateTime.of(2018, 12,3, 0,0)
        val dateTwo = LocalDateTime.of(2018, 12,2, 0,0)

        val clockEvent = ClockEvent(ClockEventType.IN, dateTwo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        val clockStatsManager = ClockEventStatsManager()

        val outPut = clockStatsManager.getLastClockLastClockType(date, clockEvent)
        Assert.assertEquals(LastClock.NEW_WEEK, outPut)
    }

    @Test
    fun clockType_isCorrectNewMonth()
    {
        val date = LocalDateTime.of(2018, 12,1, 0,0)
        val dateTwo = LocalDateTime.of(2018, 11,1, 0,0)
        val clockEvent = ClockEvent(ClockEventType.IN, dateTwo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        val clockStatsManager = ClockEventStatsManager()

        val outPut = clockStatsManager.getLastClockLastClockType(date, clockEvent)
        Assert.assertEquals(LastClock.NEW_MONTH, outPut)
    }
}