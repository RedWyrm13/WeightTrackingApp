package com.example.weighttracking.ui.theme.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(private val calendarRepositoryImplementation: CalendarRepositoryImplementation): ViewModel() {
    var isLoading = mutableStateOf(true)
        private set


    val today = calendarRepositoryImplementation.today


    private suspend fun getDatesWithWeights(startDate: LocalDate, daysToSubtract: Int = 63): List<CalendarDate> {
        return calendarRepositoryImplementation.getDatesWithWeights(startDate, daysToSubtract)
    }


    var calendarDates = mutableStateOf<List<CalendarDate>>(emptyList())
        private set

    fun refreshCalendar() {
        viewModelScope.launch {
            delay(150)

            try {
                val dates = getDatesWithWeights(today.date)
                calendarDates.value = dates
                Log.d("CalendarViewModel", "Refreshed calendar dates: $dates")
            } catch (e: Exception) {
                Log.e("CalendarViewModel", "Error refreshing calendar dates", e)
            }
        }
    }

    init {
        viewModelScope.launch {
            try {
                val dates = getDatesWithWeights(today.date,)
                calendarDates.value = dates
            } catch (e: Exception) {
                calendarDates.value = emptyList() // Provide a fallback
            } finally {
                isLoading.value = false
            }
        }
    }


}
