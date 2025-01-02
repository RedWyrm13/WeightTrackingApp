package com.example.weighttracking.ui.theme.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weighttracking.WeightTrackingApplication
import com.example.weighttracking.data.CalendarDate

object AppViewModelProvider {

    val Factory = viewModelFactory {
        // Initializer for DayViewModel for a specific day
        initializer {
            val calendarRepository = weightTrackingApplication().calendarRepository
            val calendarDay = this[CreationExtrasKey.CalendarDay]
                ?: throw IllegalArgumentException("CalendarDay must be provided.")
            DayViewModel(calendarRepository, calendarDay)
        }

        // Initializer for CalendarViewModel
        initializer {
            val calendarRepository = weightTrackingApplication().calendarRepository
            CalendarViewModel(calendarRepository)
        }
    }
}

/**
 * Custom key to provide a unique CalendarDay to the ViewModel initializer.
 */
object CreationExtrasKey {
    val CalendarDay = object : CreationExtras.Key<CalendarDate> {}
}

/**
 * Extension function to query for [Application] object and return an instance of
 * [WeightTrackingApplication].
 */
fun CreationExtras.weightTrackingApplication(): WeightTrackingApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WeightTrackingApplication)


fun createDayViewModelForDay(
    factory: ViewModelProvider.Factory,
    owner: ViewModelStoreOwner,
    calendarDay: CalendarDate
): DayViewModel {
    val extras = MutableCreationExtras().apply {
        this[CreationExtrasKey.CalendarDay] = calendarDay
    }
    return ViewModelProvider(owner.viewModelStore, factory, extras)[DayViewModel::class.java]
}
