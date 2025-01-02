package com.example.weighttracking.ui.theme.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import kotlinx.coroutines.launch
import java.time.DateTimeException
import java.time.LocalDate

class CalendarViewModel(private val calendarRepositoryImplementation: CalendarRepositoryImplementation): ViewModel() {
    val today = calendarRepositoryImplementation.today


    private suspend fun getDatesWithWeights(startDate: LocalDate, daysToSubtract: Int = 0): List<CalendarDate> {
        // Ensure daysToSubtract is within the valid range
        val safeDaysToSubtract = daysToSubtract.coerceAtMost(startDate.dayOfYear - 1)
        val adjustedStartDate = try {
            startDate.minusDays(safeDaysToSubtract.toLong())
        } catch (e: DateTimeException) {
            // Fallback to a valid date, e.g., start of the year
            startDate.withDayOfYear(1)
        }
        return calendarRepositoryImplementation.getDatesWithWeights(adjustedStartDate, safeDaysToSubtract)
    }


    var calendarDates: List<CalendarDate> = emptyList()
        private set



    init {
        viewModelScope.launch {
            try {
                val safeDaysToSubtract = 60.coerceAtMost(today.date.dayOfYear - 1) // Adjust as needed
                calendarDates = getDatesWithWeights(today.date, safeDaysToSubtract)
            } catch (e: Exception) {
                // Log and handle gracefully
                Log.e("CalendarViewModel", "Error initializing calendar dates", e)
                calendarDates = emptyList() // Provide a fallback
            }
        }
    }


}