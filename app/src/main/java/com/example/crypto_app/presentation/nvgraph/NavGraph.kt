package com.example.crypto_app.presentation.nvgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.crypto_app.presentation.navigator.AppNavigatorScreen
import com.example.crypto_app.presentation.navigator.AppNavigatorViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppNavigation.route,
            startDestination = Route.AppNavigatorScreen.route
        ) {
            composable(route = Route.AppNavigatorScreen.route) {
                val viewModel: AppNavigatorViewModel = hiltViewModel()
                AppNavigatorScreen(mainViewModel = viewModel)
            }
        }
    }
}