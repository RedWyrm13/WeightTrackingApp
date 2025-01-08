package com.example.weighttracking.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weighttracking.data.AbbreviatedDay
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModel
import java.time.LocalDate

@Composable
fun Calendar(screenViewModel: ScreenViewModel, calendarViewModel: CalendarViewModel) {
    Header(screenViewModel = screenViewModel)
    CalendarDates(calendarViewModel = calendarViewModel, screenViewModel = screenViewModel)
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
fun CalendarDates(calendarViewModel: CalendarViewModel, screenViewModel: ScreenViewModel){

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
        LaunchedEffect(Unit) {
            listState.scrollToItem(calendarDates.size - 1)
        }
        LazyRow(state = listState) {
            items(items = calendarDates.reversed()) { date ->
                CalendarItem(calendarDate = date, screenViewModel= screenViewModel)
            }
        }

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



            if (calendarDate.weight != 0.0)
                Text(text = "${calendarDate.weight}",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodySmall,
                    color = color)

        }
    }

}