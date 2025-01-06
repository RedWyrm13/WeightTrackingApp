package com.example.weighttracking.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun Header(calendarViewModel: CalendarViewModel) {
    // I can probably get rid of the Row composable, but we will keep it for now in case I want to add
    // more to it later
    Row(
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Text(text = "Today",
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
        Header(calendarViewModel = calendarViewModel)
        Calendar(calendarViewModel = calendarViewModel, screenViewModel = screenViewModel)
    }
}

@Composable
fun CalendarItem(
    calendarDate: CalendarDate,
    isSelected: Boolean,
    onCardClick: () -> Unit = {}){
    //Abbreviation for the day of the week
    val abb = AbbreviatedDay.fromDayOfWeek(calendarDate.date.dayOfWeek.value)
    val dayOfMonth = calendarDate.date.dayOfMonth.toString()
    var color = Color.Black

    Card(
       modifier = Modifier
           .padding(vertical = 4.dp, horizontal = 4.dp)
           .clickable { onCardClick() },
           colors= if (isSelected){
               CardDefaults.cardColors(containerColor = Color.Black)
           } else {
               CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
           },
        elevation = if (isSelected) CardDefaults.cardElevation(8.dp) else CardDefaults.cardElevation(2.dp),


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
                val isSelected = !(date ==screenViewModel.selectedDate)
                CalendarItem(calendarDate = date, isSelected = isSelected)
            }
        }

    }
    }





@Preview(showSystemUi = true)
@Composable
fun WeightAppPreview() {
    WeightTrackingApp()
}

