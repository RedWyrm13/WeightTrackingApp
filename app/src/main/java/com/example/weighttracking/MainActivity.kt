package com.example.weighttracking

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.weighttracking.data.CalendarDao
import com.example.weighttracking.data.CalendarDatabase
import com.example.weighttracking.data.CalendarRepositoryImplementation
import com.example.weighttracking.ui.theme.WeightTrackingApp
import com.example.weighttracking.ui.theme.WeightTrackingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeightTrackingTheme {
                WeightTrackingApp()
            }
        }
    }
}

class WeightTrackingApplication : Application() {
    lateinit var calendarRepository: CalendarRepositoryImplementation
        private set

    override fun onCreate() {
        super.onCreate()
        val calendarDao: CalendarDao = CalendarDatabase.getDatabase(applicationContext).CalendarDao()
        calendarRepository = CalendarRepositoryImplementation(calendarDao)
    }
}