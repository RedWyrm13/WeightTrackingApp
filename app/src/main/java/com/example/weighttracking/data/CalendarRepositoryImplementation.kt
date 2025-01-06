package com.example.weighttracking.data

import android.util.Log
import java.time.LocalDate

class CalendarRepositoryImplementation(private val calendarDao: CalendarDao): CalendarRepository {

    // THis overrides the interface implementation for recordWeight. It uses the CalendarDao method
    //.recordWeight() to insert the data into the calendar_dates database
    override suspend fun recordWeight(calendarDate: CalendarDate) {
        Log.d("CalendarRepository", "recordWeight: $calendarDate")
        calendarDao.recordWeight(calendarDate)
    }

    // This uses the Dao to access the database and gets the CalendarDate for the specified date
    override suspend fun getWeightForDate(date: LocalDate): CalendarDate?   {
        return calendarDao.getWeightForDate(date)
    }


    override suspend fun getDatesWithWeights(startDate: LocalDate, daysToSubtract: Int): List<CalendarDate> {
        val endDate = startDate.minusDays(daysToSubtract.toLong())
        val dbResults = calendarDao.getDatesWithWeights(endDate, startDate).associateBy { it.date }

        // Create a complete list of dates from today to the last 62 days
        val fullList = (0..62).map { index ->
            val date = startDate.minusDays(index.toLong() - 1)
            // If a date exists in the database results, use it; otherwise, create a default entry
            dbResults[date] ?: CalendarDate(date, 0.0)
        }

        return fullList
    }


    val today = CalendarDate()
}