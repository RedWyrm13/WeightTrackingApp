package com.example.weighttracking.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDate

@Dao
interface CalendarDao {

    //This adds a new weight to the calendar, and overrides a weight if it is already there
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordWeight(date: CalendarDate)

    // Gets the weight for a specific date
    @Query("SELECT * FROM calendar_dates WHERE date = :date")
     suspend fun getWeightForDate(date: LocalDate): CalendarDate


    // Gets all the dates and weights in the specified range
    @Query("SELECT * FROM CALENDAR_DATES where date between :startDate and :endDate")
     suspend fun getDatesWithWeights(startDate: LocalDate, endDate: LocalDate): List<CalendarDate>

}

