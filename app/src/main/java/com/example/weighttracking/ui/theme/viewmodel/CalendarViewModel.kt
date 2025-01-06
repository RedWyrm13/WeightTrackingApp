package com.example.weighttracking.ui.theme.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import kotlinx.coroutines.launch
import java.time.DateTimeException
import java.time.LocalDate

class CalendarViewModel(private val calendarRepositoryImplementation: CalendarRepositoryImplementation): ViewModel() {
    var isLoading = mutableStateOf(true)
        private set


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
                if (calendarDates.isEmpty()) {
                    calendarDates = List(60) { CalendarDate(today.date.minusDays(it.toLong()), 0.0) }
                }
            } catch (e: Exception) {
                Log.e("CalendarViewModel", "Error initializing calendar dates", e)
            } finally {
                isLoading.value = false
            }
        }
    }


    fun getDateRange(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        require(!startDate.isAfter(endDate)) { "Start date must be before or equal to end date" }

        return generateSequence(startDate) { date ->
            if (date.isBefore(endDate)) date.plusDays(1) else null
        }.toList()
    }

}
