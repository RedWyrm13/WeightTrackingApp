
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.DayViewModel

class DayViewModelFactory(
    private val repository: CalendarRepositoryImplementation,
    private val calendarDay: CalendarDate
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DayViewModel::class.java)) {
            return DayViewModel(repository, calendarDay) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class CalendarViewModelFactory(
    private val repository: CalendarRepositoryImplementation
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

fun createDayViewModelForDay(
    repository: CalendarRepositoryImplementation,
    owner: ViewModelStoreOwner,
    calendarDay: CalendarDate
): DayViewModel {
    val factory = DayViewModelFactory(repository, calendarDay)
    return ViewModelProvider(owner, factory)
        .get("DayViewModel_${calendarDay.date}", DayViewModel::class.java)
}
