package com.example.weighttracking.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.weighttracking.data.CalendarRepositoryImplementation

class UserViewModel(private val calendarRepositoryImplementation: CalendarRepositoryImplementation): ViewModel() {
    var currentWeightInput by mutableStateOf("")

    // This is used to determine if we use kg or lbs. True is lbs.  False is kg
    var isPounds: Boolean by mutableStateOf(true)

    val currentWeihgt: Double
        get() = currentWeightInput.toDoubleOrNull() ?: 0.0


}