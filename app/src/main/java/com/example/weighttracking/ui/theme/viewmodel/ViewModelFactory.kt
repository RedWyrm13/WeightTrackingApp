package com.example.weighttracking.ui.theme.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weighttracking.WeightTrackingApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for CalendarViewModel
        initializer {
            val calendarRepository = weightTrackingApplication().calendarRepository
            DayViewModel(calendarRepository)
        }

        initializer {
            val calendarRepository = weightTrackingApplication().calendarRepository
            CalendarViewModel(calendarRepository)
        }
    }
}

/**
 * Extension function to query for [Application] object and return an instance of
 * [WeightTrackingApplication].
 */
fun CreationExtras.weightTrackingApplication(): WeightTrackingApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WeightTrackingApplication)
