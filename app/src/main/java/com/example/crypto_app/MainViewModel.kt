package com.example.crypto_app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.crypto_app.presentation.nvgraph.Route

class MainViewModel(): ViewModel() {
    var startDestination by mutableStateOf(Route.AppNavigation.route)
        private set
}