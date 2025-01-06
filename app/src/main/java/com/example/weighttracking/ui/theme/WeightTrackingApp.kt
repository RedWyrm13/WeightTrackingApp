package com.example.weighttracking.ui.theme

import CalendarViewModelFactory
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.weighttracking.R
import com.example.weighttracking.WeightTrackingApplication
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.DayViewModel
import createDayViewModelForDay


@Composable
fun WeightTrackingApp() {

    // Get the repository from the application context
    val repository = LocalContext.current.applicationContext
        .let { it as WeightTrackingApplication }.calendarRepository

    // Get the ViewModelStoreOwner (typically the current composable's context)
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: throw IllegalStateException("ViewModelStoreOwner is not available.")

    // Use ViewModelProvider with the custom CalendarViewModelFactory
    val calendarViewModel: CalendarViewModel = remember {
        ViewModelProvider(viewModelStoreOwner, CalendarViewModelFactory(repository))
            .get(CalendarViewModel::class.java)
    }

    CalendarApp(calendarViewModel = calendarViewModel)
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
fun CalendarApp(calendarViewModel: CalendarViewModel, modifier: Modifier = Modifier) {


    Column(modifier = modifier
        .fillMaxSize()
        .safeDrawingPadding(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        TopRowContent(calendarViewModel = calendarViewModel)
    }
}
@Composable
fun TopRowContent(calendarViewModel: CalendarViewModel){
    Column(){
        Header(calendarViewModel = calendarViewModel)
        Calendar(calendarViewModel = calendarViewModel)
    }
}

@Composable
fun CalendarItem(dayViewModel: DayViewModel){

    val calendarDate = dayViewModel.date
    Log.d("CalendarItem", "Date passed to CalendarItem: $calendarDate")

    Card(
       modifier = Modifier
           .padding(vertical = 4.dp, horizontal = 4.dp),
           colors= CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),

    ){
        Column(
            modifier = Modifier
                .width(48.dp)
                .height(72.dp)
                .padding(4.dp)
        ){
            if (calendarDate.date.dayOfMonth.toString() == "1") {
                Text(text = calendarDate.date.month.toString(),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = calendarDate.date.dayOfWeek.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall)

            Text(text = calendarDate.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium)


            Text(text = "${calendarDate.weight}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall)
        }
    }

}

@Composable
fun Calendar(calendarViewModel: CalendarViewModel) {
    val isLoading by calendarViewModel.isLoading
    val calendarDates = calendarViewModel.calendarDates.value // Access the value of mutableStateOf
    Log.d("CalendarDates", "${calendarDates}")

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else {
        val viewModelStoreOwner = LocalViewModelStoreOwner.current
            ?: throw IllegalStateException("ViewModelStoreOwner is not available.")

        val repository = LocalContext.current.applicationContext
            .let { it as WeightTrackingApplication }.calendarRepository

        val listState = rememberLazyListState()

        LazyRow(state = listState) {
            items(items = calendarDates) { date ->
                Log.d("LazyRowDate", "Date passed to LazyRow: $date")
                val dayViewModel = createDayViewModelForDay(repository, viewModelStoreOwner, date)
                CalendarItem(dayViewModel = dayViewModel)
            }
        }

    }
}



@Composable
fun MainContent(dayViewModel: DayViewModel){
    val date = dayViewModel.date
    var  weight by remember { mutableStateOf(date.weight.toString()) }
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
    WeightTrackingApp()
}

