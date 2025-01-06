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

class ScreenViewModel(private val calendarRepositoryImplementation: CalendarRepositoryImplementation): ViewModel() {
    lateinit var today: CalendarDate
    var selectedDate by mutableStateOf(today)

    val date: LocalDate
            get() = selectedDate.date
    val weight: Double
            get() = selectedDate.weight



// This is run upon initialization of the viewmodel
    init {
        viewModelScope.launch {
            today = calendarRepositoryImplementation.getWeightForDate(LocalDate.now()) ?: CalendarDate()
            selectedDate = today
        }
    }

}
