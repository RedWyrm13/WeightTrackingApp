package com.example.weighttracking.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weighttracking.data.CalendarDao
import com.example.weighttracking.data.CalendarDate
import com.example.weighttracking.data.CalendarRepositoryImplementation
import com.example.weighttracking.ui.theme.viewmodel.UserViewModel
import java.time.LocalDate

@Composable
fun StartScreen(userViewModel: UserViewModel, onSignInButtonClicked: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize().safeContentPadding(), color = Color.White) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {

            Text(text = "Welcome to ", color = Color.Black, fontSize = 30.sp)
            Text(text = "Weight Tracking!", color = Color.Black, fontSize = 30.sp)


            StartScreenMainContent(
                userViewModel = userViewModel,
                onSignInButtonClicked = onSignInButtonClicked,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun StartScreenMainContent(userViewModel: UserViewModel, onSignInButtonClicked: () -> Unit, modifier : Modifier = Modifier) {
    var enabled by remember { mutableStateOf(false) }

    if (userViewModel.currentWeightInput.isNotEmpty()) {
        enabled = true
    }
Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Text(text = "Please enter your current weight:", color = Color.Black, fontSize = 24.sp)
    Spacer(Modifier.padding(16.dp))
    CurrentWeightTextFIeld(userViewModel = userViewModel)
    Boxes(userViewModel = userViewModel)
    ButtonsForSignIn(
        userViewModel = userViewModel,
        onSignInButtonClicked = onSignInButtonClicked
    )
}

}

@Composable
fun CurrentWeightTextFIeld(userViewModel: UserViewModel) {
    val weightInput = userViewModel.currentWeightInput
    val onWeightChange = {newWeight: String -> userViewModel.currentWeightInput = newWeight}
    val keyboardController = LocalSoftwareKeyboardController.current


    TextField(
        value = weightInput,
        onValueChange = onWeightChange,
        placeholder = { Text("Enter Weight Here") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {  keyboardController?.hide() }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
fun Boxes(userViewModel: UserViewModel){

    //On Signin we will update the viewmodel with the selected unit
    var isSelected by remember { mutableStateOf(userViewModel.isPounds) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PoundsBox(isSelected = isSelected){ selected -> isSelected = selected }
        Spacer(Modifier.padding(16.dp))
        KgBox(isSelected = isSelected){ selected -> isSelected = !selected }
    }
}

@Composable
fun ButtonsForSignIn(userViewModel: UserViewModel, onSignInButtonClicked: () -> Unit) {
    val isEnabled = userViewModel.currentWeightInput.isNotEmpty()

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {},
            enabled = isEnabled, // Dynamically enable/disable based on input
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
        ) {
            Text("Sign In With Email", color = Color.Black)
        }
        Button(
            onClick = {},
            enabled = isEnabled,
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
        ) {
            Text("Sign In With Phone", color = Color.Black)
        }
        Button(
            onClick = {},
            enabled = isEnabled,
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
        ) {
            Text("Sign In With Google", color = Color.Black)
        }
        Button(
            onClick = {},
            enabled = isEnabled,
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
        ) {
            Text("Sign In With Facebook", color = Color.Black)
        }
    }
}

@Composable
fun PoundsBox(isSelected: Boolean, onClick: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick(true) }
            .background(if (isSelected) Color.Green else Color.Transparent) // Example visual feedback
    ) {
        Text("lbs", color = Color.Black, fontSize = 24.sp)
    }
}

@Composable
fun KgBox(isSelected: Boolean, onClick: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick(true) }
            .background(if (isSelected) Color.Transparent else Color.Green) // Example visual feedback
    ) {
        Text("Kg", color = Color.Black, fontSize = 24.sp)
    }
}





@Preview
@Composable
fun StartScreenPreview() {
    val fakeRepository = CalendarRepositoryImplementation(calendarDao = object : CalendarDao {
        override suspend fun recordWeight(date: CalendarDate) {
            // No-op
        }

        override suspend fun getWeightForDate(date: LocalDate): CalendarDate? {
            return CalendarDate(date, weight = 75.0)
        }

        override suspend fun getDatesWithWeights(startDate: LocalDate, endDate: LocalDate): List<CalendarDate> {
            return emptyList()
        }
    })

    val userViewModel = UserViewModel(calendarRepositoryImplementation = fakeRepository)
    StartScreen(userViewModel, {})
}