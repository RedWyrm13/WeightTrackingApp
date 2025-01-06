package com.example.weighttracking.data

import android.util.Log
import java.time.LocalDate

class CalendarRepositoryImplementation(private val calendarDao: CalendarDao): CalendarRepository {

    // THis overrides the interface implementation for recordWeight. It uses the CalendarDao method
    //.recordWeight() to insert the data into the calendar_dates database
    override suspend fun recordWeight(calendarDate: CalendarDate) {
        calendarDao.recordWeight(calendarDate)
    }

    // This uses the Dao to access the database and gets the CalendarDate for the specified date
    override suspend fun getWeightForDate(date: LocalDate): CalendarDate?   {
        return calendarDao.getWeightForDate(date)
    }


    override suspend fun getDatesWithWeights(startDate: LocalDate, daysToSubtract: Int): List<CalendarDate> {
        val endDate = startDate.minusDays(daysToSubtract.toLong())
        val result = calendarDao.getDatesWithWeights(startDate, endDate)
        return result.ifEmpty { createList() }
    }

    private fun createList(): List<CalendarDate> {
        return List(64) { index ->
            val date = today.date.minusDays(index -1.toLong())
            val weight = index.toDouble() * 2.0
            Log.d("CreateList", "Generated date: $date")
            CalendarDate(date, weight)
        }
    }

    val today = CalendarDate()
}