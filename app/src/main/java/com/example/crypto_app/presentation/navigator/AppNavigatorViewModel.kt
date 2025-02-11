package com.example.crypto_app.presentation.navigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.crypto_app.R
import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.domain.usecases.auth.AuthUseCases
import com.example.crypto_app.presentation.authentication.AuthState
import com.example.crypto_app.presentation.navigator.components.BottomNavigationItem
import com.example.crypto_app.presentation.nvgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppNavigatorViewModel @Inject constructor(private val authUseCases: AuthUseCases) :
    ViewModel() {
    var startingRoute by mutableStateOf(Route.LoginScreen.route)
        private set

    val bottomNavigationItem =
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_person, text = "Profile"),
        )
    var selectedItem by mutableIntStateOf(0)
        private set

    var selectedChatChannelId by mutableStateOf<String?>(null)
        private set

    init {
        startingRoute = if (authUseCases.isSignIn()) {
            Route.ChatScreen.route
        } else {
            Route.LoginScreen.route
        }
    }

    fun changeSelectedChatChannelId(value: String? = null) {
        selectedChatChannelId = value
    }

    fun changeRoute(navController: NavHostController, authState: AuthState) {
        if (authState == AuthState.Authenticated) {
            navController.navigate(route = Route.ChatScreen.route) {
                popUpTo(Route.LoginScreen.route) { inclusive = true }
            }
        } else if (authState == AuthState.UnAuthenticated) {
            navController.navigate(route = Route.LoginScreen.route) {
                popUpTo(Route.ChatScreen.route) { inclusive = true }
            }
        }
    }

    fun changeTab(index: Int) {
        selectedItem = index
    }

    fun navigateToDetails(navController: NavHostController, coinId: String) {
        navController.currentBackStackEntry?.savedStateHandle?.set("coinId", coinId)
        navController.navigate(
            route = Route.DetailCoinScreen.route
        ) {
            anim {

            }
        }
    }

    fun navigateToAttachmentsScreen(
        navController: NavHostController, attachments: List<Attachment>, index: Int
    ) {
        navController.currentBackStackEntry?.savedStateHandle?.set("attachments", attachments)
        navController.currentBackStackEntry?.savedStateHandle?.set("index", index)
        navController.navigate(route = Route.AttachmentsScreen.route)
    }

    fun navigateToChatDetail(navController: NavHostController, chatChannelId: String) {
        navController.currentBackStackEntry?.savedStateHandle?.set("chatChannelId", chatChannelId)
        navController.navigate(
            route = Route.DetailChatScreen.route,
        )
    }

    fun navigateToTab(navController: NavHostController, route: String) {
        navController.navigate(route) {
            navController.graph.startDestinationRoute?.let { homeScreen ->
                popUpTo(homeScreen) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true //if in homeScreen and click multiple time
                // on home screen then just launch the same screen
            }
        }
    }
}
