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
    val dataPairs: List<Pair<String, Float>>
        get() = if (calendarDates.value.isEmpty()) emptyList() else getData(calendarDates.value)


    val lastSevenDayAverage: Double
        get() = if (calendarDates.value.isEmpty())  0.0 else calendarDates.value.slice(1..7).map { it.weight }.average()

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
    fun getData(list: List<CalendarDate>): List<Pair<String, Float>> {
        val slices = getSlices(list)
        return slices.map { slice ->
            val oldestDate = slice.minByOrNull { it.date }?.date?.toString() ?: "Unknown"
            val averageWeight = slice.map { it.weight }.average()
            Pair(oldestDate, averageWeight.toFloat())
        }
    }
    fun getSlices(list: List<CalendarDate>): List<List<CalendarDate>> {
        return list.slice(1 until list.size).chunked(7)
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




// Extension function to convert ISO date string to a Float (e.g., day of the year)
fun String.toFloatDayOfYear(): Float {
    val localDate = LocalDate.parse(this)
    return localDate.dayOfYear.toFloat() // Using day of the year as a proxy for the date
}