package com.example.weighttracking.data

import java.time.LocalDate

class CalendarRepositoryImplementation(private val calendarDao: CalendarDao): CalendarRepository {

    // THis overrides the interface implementation for recordWeight. It uses the CalendarDao method
    //.recordWeight() to insert the data into the calendar_dates database
    override suspend fun recordWeight(date: LocalDate, weight: Double) {
        val calendarDate = CalendarDate(date, weight)
        calendarDao.recordWeight(calendarDate)
    }

    // This uses the Dao to access the database and gets the CalendarDate for the specified date
    override suspend fun getWeightForDate(date: LocalDate): CalendarDate?   {
        return calendarDao.getWeightForDate(date)
    }


    override  suspend fun getDatesWithWeights(startDate: LocalDate, daysToSubtract: Int): List<CalendarDate> {
        val endDate = startDate.minusDays(daysToSubtract.toLong())
        return calendarDao.getDatesWithWeights(startDate, endDate)
    }

    override fun getTodaysDate(): LocalDate {
        return LocalDate.now()
    }
}