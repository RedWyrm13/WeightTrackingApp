package com.example.weighttracking.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weighttracking.ui.theme.ButtonBoxAndText
import com.example.weighttracking.ui.theme.Calendar
import com.example.weighttracking.ui.theme.GraphView
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModel


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

