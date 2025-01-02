package com.example.weighttracking.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(private val calendarRepositoryImplementation: CalendarRepositoryImplementation): ViewModel() {

    val TODAY = LocalDate.now()

    private suspend fun getDatesWithWeights(startDate: LocalDate, daysToSubtract: Int = 60): List<CalendarDate> {
        return calendarRepositoryImplementation.getDatesWithWeights(startDate, daysToSubtract)

    }
    var calendarDates: List<CalendarDate> = emptyList()
        private set

    var today: CalendarDate? = null
        private set

    init{
        viewModelScope.launch{
            calendarDates = getDatesWithWeights(TODAY)
        }
        today = calendarDates[0]
    }

    var selectedDate = today

}