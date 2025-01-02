package com.example.weighttracking.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weighttracking.R
import com.example.weighttracking.ui.theme.viewmodel.AppViewModelProvider
import com.example.weighttracking.ui.theme.viewmodel.DayViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel


@Composable
fun WeightTrackingApp(modifier: Modifier = Modifier){
    val dayViewModel: DayViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
    val calendarViewModel = viewModel(factory = AppViewModelProvider.Factory)

    CalendarApp(dayViewModel = DayViewModel, calendarViewModel = calendarViewModel)

}

@Composable
fun Header(dayViewModel: DayViewModel, calendarViewModel: CalendarViewModel) {
    Row(
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Text(text = if (calendarViewModel.selectedDate == calendarViewModel.TODAY) "Today"
        else {
            calendarViewModel.selectedDate.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)))
        },
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
        IconButton(onClick = {}){
            Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = "Previous")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "Next")
        }
    }
}

@Composable
fun CalendarApp(dayViewModel: DayViewModel, calendarViewModel: CalendarViewModel, modifier: Modifier = Modifier) {


    Column(modifier = modifier
        .fillMaxSize()
        .safeDrawingPadding(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        TopRowContent(dayViewModel= dayViewModel, calendarViewModel = calendarViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        MainContent()
    }
}
@Composable
fun TopRowContent(dayViewModel: DayViewModel, calendarViewModel = calendarViewModel){
    Column(){
        Header(dayViewModel = dayViewModel)
        Calendar(dayViewModel = dayViewModel)
    }
}

@Composable
fun CalendarItem(dayViewModel: DayViewModel){

    val isToday = date.isToday


    Card(
       modifier = Modifier
           .padding(vertical = 4.dp, horizontal = 4.dp),
        colors = if (isToday) {CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)}
        else{
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)},

    ){
        Column(
            modifier = Modifier
                .width(48.dp)
                .height(72.dp)
                .padding(4.dp)
        ){
            if (date.date.dayOfMonth.toString() == "1") {
                Text(text = date.month,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall)

            Text(text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium)


            Text(text = "171.5",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun Calendar(dayViewModel: DayViewModel, calendarViewModel = calendarViewModel){
    val listState = rememberLazyListState()
    val todayIndex = calendarUiModel.visibleDates.indexOfFirst{it.isToday}

    LaunchedEffect(todayIndex) {
        if (todayIndex >= 0) {
            listState.scrollToItem(todayIndex)
        }
    }

    LazyRow(state = listState) {
        items(items = calendarUiModel.visibleDates){ date ->
                CalendarItem(date)
        }
    }
}

@Composable
fun MainContent(dayViewModel: DayViewModel, calendarViewModel = calendarViewModel){
    var  weight by remember { mutableStateOf(recordedWeight)}
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = weight, onValueChange = {weight = it})
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.save_weight))
            
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun WeightAppPreview() {
    WeightTrackingApp(
        modifier = Modifier.padding(16.dp)
    )
}

