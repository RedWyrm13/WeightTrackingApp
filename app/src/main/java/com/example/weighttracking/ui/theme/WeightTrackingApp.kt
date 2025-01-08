package com.example.weighttracking.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.weighttracking.WeightTrackingApplication
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModelFactory
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModel
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModelFactory


@Composable
fun WeightTrackingApp() {

    // Get the repository from the application context
    val repository = LocalContext.current.applicationContext
        .let { it as WeightTrackingApplication }.calendarRepository

    // Get the ViewModelStoreOwner (typically the current composables context)
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: throw IllegalStateException("ViewModelStoreOwner is not available.")

    // Use ViewModelProvider with the custom com.example.weighttracking.ui.theme.viewmodel.CalendarViewModelFactory
    val calendarViewModel: CalendarViewModel = remember {
        ViewModelProvider(
            viewModelStoreOwner,
            CalendarViewModelFactory(repository)
        )[CalendarViewModel::class.java]
    }
    val screenViewModel: ScreenViewModel = remember {
        ViewModelProvider(
            viewModelStoreOwner,
            ScreenViewModelFactory(repository))[ScreenViewModel::class.java]

    }

    MainScreen(
        calendarViewModel = calendarViewModel,
        screenViewModel = screenViewModel
    )
}




@Composable
fun MainScreen(
    calendarViewModel: CalendarViewModel,
    screenViewModel: ScreenViewModel,
    modifier: Modifier = Modifier) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(top = 12.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Calendar(screenViewModel = screenViewModel, calendarViewModel = calendarViewModel)
            ButtonBoxAndText(calendarViewModel = calendarViewModel, screenViewModel = screenViewModel)
            if (calendarViewModel.dataPairs.isNotEmpty()) {
                GraphView(data = calendarViewModel.dataPairs.reversed())
            }
        }
    }

