
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weighttracking.data.CalendarRepositoryImplementation
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel


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
