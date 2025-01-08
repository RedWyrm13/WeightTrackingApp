package com.example.weighttracking.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weighttracking.WeightTrackingApplication
import com.example.weighttracking.ui.theme.screens.StartScreen
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModel
import com.example.weighttracking.ui.theme.viewmodel.CalendarViewModelFactory
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModel
import com.example.weighttracking.ui.theme.viewmodel.ScreenViewModelFactory
import com.example.weighttracking.ui.theme.viewmodel.UserViewModel
import com.example.weighttracking.ui.theme.viewmodel.UserViewModelFactory

sealed class Screens(val route: String){
    object StartScreen: Screens("start_screen")
    object MainScreen: Screens("main_screen")
}

@Composable
fun WeightTrackingApp() {
    val navController = rememberNavController()

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
            ScreenViewModelFactory(repository)
        )[ScreenViewModel::class.java]

    }

    val userViewModel: UserViewModel = remember {
        ViewModelProvider(
            viewModelStoreOwner,
            UserViewModelFactory(repository)
        )[UserViewModel::class.java]
    }

    NavHost(navController = navController, startDestination = Screens.StartScreen.route) {
        composable(route = Screens.StartScreen.route) {
            StartScreen(
                userViewModel = userViewModel,
                onSignInButtonClicked = { navController.navigate(Screens.MainScreen.route) })
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(calendarViewModel = calendarViewModel, screenViewModel = screenViewModel)
        }
    }
}