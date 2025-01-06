package com.example.weighttracking.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.weighttracking.WeightTrackingApplication
import com.example.weighttracking.data.AbbreviatedDay
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModelFactory
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModel
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModelFactory
import java.time.LocalDate


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

    CalendarApp(
        calendarViewModel = calendarViewModel,
        screenViewModel = screenViewModel
    )
}


@Composable
fun Header(screenViewModel: ScreenViewModel) {
    // I can probably get rid of the Row composable, but we will keep it for now in case I want to add
    // more to it later
    val text = screenViewModel.formattedDate
    Row(
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        if (screenViewModel.selectedDate.date == LocalDate.now())
            Text(text = "Today, ")
        if (screenViewModel.selectedDate.date == LocalDate.now().plusDays(1))
            Text(text = "Tomorrow, ")
        if (screenViewModel.selectedDate.date == LocalDate.now().minusDays(1))
            Text(text = "Yesterday, ")

        Text(text = text,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
    }
}

@Composable
fun CalendarApp(
    calendarViewModel: CalendarViewModel,
    screenViewModel: ScreenViewModel,
    modifier: Modifier = Modifier) {


    Column(modifier = modifier
        .fillMaxSize()
        .safeDrawingPadding(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        TopRowContent(calendarViewModel = calendarViewModel, screenViewModel = screenViewModel)
    }
}
@Composable
fun TopRowContent(calendarViewModel: CalendarViewModel, screenViewModel: ScreenViewModel) {
        Column {
        Header(screenViewModel = screenViewModel)
        Calendar(calendarViewModel = calendarViewModel, screenViewModel = screenViewModel)
        ButtonBox(screenViewModel = screenViewModel, calendarViewModel = calendarViewModel)
    }
}

@Composable
fun CalendarItem(
    calendarDate: CalendarDate,
    screenViewModel: ScreenViewModel){
    //Abbreviation for the day of the week
    val abb = AbbreviatedDay.fromDayOfWeek(calendarDate.date.dayOfWeek.value)
    val dayOfMonth = calendarDate.date.dayOfMonth.toString()

    val isSelected = calendarDate.date == screenViewModel.selectedDate.date
    var color = Color.Black


    Card(
       modifier = Modifier
           .padding(vertical = 4.dp, horizontal = 4.dp)
           .clickable {
               screenViewModel.selectedDate = calendarDate
               screenViewModel.fetchWeightForDate(screenViewModel.selectedDate.date)
                      },
           colors= if (isSelected){
               CardDefaults.cardColors(containerColor = Color.Black)

           } else {
               CardDefaults.cardColors(containerColor = Color.Transparent)

           },
        shape= CircleShape,
    ){
        Column(
            modifier = Modifier
                .width(48.dp)
                .height(72.dp)
                .padding(4.dp)
        ){
            if (isSelected){
                color = Color.White
            }

            Text(text = abb.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
                color = color)

            Text(text = dayOfMonth,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
                color = color)



            Text(text = "${calendarDate.weight}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
                color = color)

        }
    }

}

@Composable
fun Calendar(calendarViewModel: CalendarViewModel, screenViewModel: ScreenViewModel){

    val isLoading by calendarViewModel.isLoading
    val calendarDates = calendarViewModel.calendarDates.value // Access the value of mutableStateOf
    val listState = rememberLazyListState()


    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
    else{
        LazyRow(state = listState) {
            items(items = calendarDates.reversed()) { date ->
                CalendarItem(calendarDate = date, screenViewModel= screenViewModel)
            }
        }

    }
}

@Composable
fun ButtonBox(screenViewModel: ScreenViewModel,
              calendarViewModel: CalendarViewModel) {
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
            screenViewModel.fetchWeightForDate(screenViewModel.selectedDate.date)

        },
            enabled = if (screenViewModel.weightInput.isNotEmpty()) true else false) {
            Text("Save Weight")
        }
        Text(screenViewModel.selectedDateWeight.value.toString())
    }
}





@Preview(showSystemUi = true)
@Composable
fun WeightAppPreview() {
    WeightTrackingApp()
}

