package com.example.weighttracking.data

import java.time.LocalDate

interface CalendarRepository {

    // Adds the weight to the Rooms database in the device memory
    suspend fun recordWeight(calendarDate: CalendarDate)

    // This gets the weight for the day
    suspend fun getWeightForDate(date: LocalDate): CalendarDate?

    // This gets all the dates and weights in the specified range, which is 60 days
    suspend fun getDatesWithWeights(startDate: LocalDate, daysToSubtract: Int = 63): List<CalendarDate>




}