package com.example.weighttracking.data

import androidx.room.Entity
import java.time.LocalDate

@Entity(tableName = "calendar_dates")
data class CalendarDate(
    val date: LocalDate = LocalDate.now(),
    val weight: Double = 0.0
    )