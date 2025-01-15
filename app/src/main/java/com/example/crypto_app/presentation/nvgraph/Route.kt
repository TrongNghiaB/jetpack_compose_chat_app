package com.example.crypto_app.presentation.nvgraph

sealed class Route(val route: String) {
    data object OnBoardingScreen: Route(route = "onBoardingScreen")
    data object HomeScreen: Route(route = "homeScreen")
    data object DetailCoinScreen: Route(route = "detailCoinScreen")
    data object SearchScreen: Route(route = "searchScreen")
    data object ProfileScreen: Route(route = "profileScreen")
    data object TransactionScreen: Route(route = "transactionScreen")
    data object ChatScreen: Route(route = "chatScreen")
    data object AttachmentsScreen: Route(route = "attachmentsScreen")
    data object DetailChatScreen: Route(route = "detailChatScreen")
    data object WalletsScreen: Route(route = "walletsScreen")
    data object AppStartNavigation: Route(route = "appStartNavigation")

    //auth

    data object LoginScreen: Route(route = "loginScreen")
    data object SignUpScreen: Route(route = "signUpScreen")

    data object AppNavigation: Route(route = "appNavigation")
    data object AppNavigatorScreen: Route(route = "appNavigatorScreen")
}