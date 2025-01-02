package com.example.weighttracking.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import kotlinx.coroutines.launch
import java.time.LocalDate

class DayViewModel(
    private val calendarRepositoryImplementation: CalendarRepositoryImplementation,
     val date: CalendarDate) : ViewModel() {


    suspend fun getWeightForDate(date: LocalDate): CalendarDate? {
        return calendarRepositoryImplementation.getWeightForDate(date)
    }

    fun recordWeight(date: LocalDate, weight: Double) {
        viewModelScope.launch {
            calendarRepositoryImplementation.recordWeight(date, weight)
        }
    }
}



    /*

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val firstDayOfWeek = startDate.withDayOfYear(today.dayOfYear - 90)
        val endDayOfWeek = firstDayOfWeek.plusDays(91)
        val visibileDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)

        return toUiModel(visibileDates, lastSelectedDate)
    }

    fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)

        return Stream.iterate(startDate) { it.plusDays(1) }
            .limit(numOfDays+1)
            .collect(Collectors.toList())
    }

    private fun toUiModel(dateList: List<LocalDate>, lastSelectedDate: LocalDate): CalendarUiModel {
        return CalendarUiModel(selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map { toItemUiModel(it, it.isEqual(lastSelectedDate)) })
    }

    private fun toItemUiModel(date: LocalDate, isSelected: Boolean): CalendarUiModel.Date {
        return CalendarUiModel.Date(date = date, isSelected = isSelected, isToday = date.isEqual(today))
    }
*/
