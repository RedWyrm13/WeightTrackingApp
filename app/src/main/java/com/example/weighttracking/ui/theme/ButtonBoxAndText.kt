package com.example.weighttracking.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModel

@Composable
fun ButtonBoxAndText(calendarViewModel: CalendarViewModel, screenViewModel: ScreenViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Last 7 days average: %.2f lbs".format(calendarViewModel.lastSevenDayAverage))

        ButtonBox(screenViewModel = screenViewModel, calendarViewModel = calendarViewModel)
    }
}


@Composable
fun ButtonBox(screenViewModel: ScreenViewModel,
              calendarViewModel: CalendarViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        TextField(
            value = screenViewModel.weightInput,
            onValueChange = { screenViewModel.weightInput = it}, // Updates the text state
            label = { Text("Weight") }, // Label shown above the text field
            placeholder = { Text("Enter weight") }, // Hint text
        )
        Button(onClick = {
            screenViewModel.updateWeight(screenViewModel.weightInput.toDouble())
            calendarViewModel.refreshCalendar()
            screenViewModel.weightInput = ""

        },
            enabled = if (screenViewModel.weightInput.isNotEmpty()) true else false) {
            Text("Save Weight")
        }
    }
}


