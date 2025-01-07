package com.example.weighttracking.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    var initialWeight = mutableStateOf("")
    var isPounds = mutableStateOf(true)
}