package com.example.weighttracking.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScreenViewModel(private val calendarRepositoryImplementation: CalendarRepositoryImplementation): ViewModel() {
    // THe following three variables are used in the composable for display
    var today: CalendarDate = CalendarDate()
        private set

    var selectedDate by mutableStateOf(today)

    private val date: LocalDate
            get() = selectedDate.date
    private val weight: Double
            get() = selectedDate.weight

    val weightInput by mutableStateOf(weight)
    val formattedDate: String
            get() = selectedDate.date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))

    //Add weight to database on button click from composable
    fun updateWeight(weight: Double) {
        viewModelScope.launch {
            calendarRepositoryImplementation.recordWeight(date, weight)
            selectedDate = CalendarDate(date, weight)
        }
    }

// This is run upon initialization of the viewmodel



    init {
        viewModelScope.launch {
            today = calendarRepositoryImplementation.getWeightForDate(LocalDate.now()) ?: CalendarDate()
            selectedDate = today
        }
    }

}
