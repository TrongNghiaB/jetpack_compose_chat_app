package com.example.crypto_app.presentation.navigator

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.presentation.attachments.AttachmentsScreen
import com.example.crypto_app.presentation.attachments.AttachmentsViewModel
import com.example.crypto_app.presentation.authentication.AuthViewModel
import com.example.crypto_app.presentation.authentication.signin.SignInScreen
import com.example.crypto_app.presentation.authentication.signup.SignUpScreen
import com.example.crypto_app.presentation.chat.ChatScreen
import com.example.crypto_app.presentation.chat.ChatViewModel
import com.example.crypto_app.presentation.chat.chat_detail.ChatDetailScreen
import com.example.crypto_app.presentation.chat.chat_detail.ChatDetailViewModel
import com.example.crypto_app.presentation.details.DetailCoinScreen
import com.example.crypto_app.presentation.details.DetailCoinViewModel
import com.example.crypto_app.presentation.home.HomeScreen
import com.example.crypto_app.presentation.home.HomeViewModel
import com.example.crypto_app.presentation.navigator.components.NewsBottomNavigation
import com.example.crypto_app.presentation.nvgraph.Route
import com.example.crypto_app.presentation.profile.ProfileScreen
import com.example.crypto_app.presentation.profile.ProfileViewModel
import com.example.crypto_app.presentation.search.SearchScreen
import com.example.crypto_app.presentation.search.SearchViewModel
import com.example.crypto_app.presentation.transaction.TransactionScreen

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigatorScreen(
    modifier: Modifier = Modifier,
    mainViewModel: AppNavigatorViewModel,
) {
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value

    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route
                || backstackState?.destination?.route == Route.ProfileScreen.route
                || backstackState?.destination?.route == Route.ChatScreen.route
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = mainViewModel.bottomNavigationItem,
                    selected = mainViewModel.selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> {
                                mainViewModel.navigateToTab(
                                    navController = navController,
                                    route = Route.ChatScreen.route
                                )
                                mainViewModel.changeTab(0)
                            }

                            1 -> {
                                mainViewModel.navigateToTab(
                                    navController = navController,
                                    route = Route.SearchScreen.route
                                )
                                mainViewModel.changeTab(1)
                            }

                            2 -> {
                                mainViewModel.navigateToTab(
                                    navController = navController,
                                    route = Route.ProfileScreen.route
                                )
                                mainViewModel.changeTab(2)
                            }
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        val homeViewModel: HomeViewModel = hiltViewModel()
        val authViewModel: AuthViewModel = hiltViewModel()
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val chatViewModel: ChatViewModel = hiltViewModel()

        val startingRoute = remember {
            mainViewModel.startingRoute
        }
        val authState = authViewModel.authState

        LaunchedEffect(authState) {
            mainViewModel.changeRoute(navController = navController, authState = authState)
        }

        NavHost(
            navController = navController,
            startDestination = startingRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            modifier = modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.ChatScreen.route) {
                ChatScreen(
                    chatViewModel = chatViewModel,
                    navigateToSearch = {
                        mainViewModel.navigateToTab(
                            navController,
                            Route.SearchScreen.route
                        )
                    },
                    navigateToDetailChat = { channelId ->
                        mainViewModel.navigateToChatDetail(navController, channelId)
                    })
            }

            composable(
                route = Route.DetailChatScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
                ) {
                if (mainViewModel.selectedChatChannelId == null) {
                    mainViewModel.changeSelectedChatChannelId(
                        navController
                            .previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<String>("chatChannelId")
                    )
                }
                mainViewModel.selectedChatChannelId?.let { chatChannelId ->
                    val chatDetailViewModel: ChatDetailViewModel = hiltViewModel()
                    ChatDetailScreen(
                        chatChannelId = chatChannelId,
                        chatViewModel = chatViewModel,
                        chatDetailViewModel = chatDetailViewModel,
                        navigateToAttachmentsScreen = { attachments, index ->
                            mainViewModel.navigateToAttachmentsScreen(
                                navController,
                                attachments,
                                index
                            )
                        },
                        onBackClick = {
                            mainViewModel.changeSelectedChatChannelId()
                            navController.popBackStack()
                        },
                    )
                }
            }

            composable(route = Route.HomeScreen.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    navigateToDetails = { coinId ->
                        mainViewModel.navigateToDetails(navController, coinId)
                    },
                    navigateToSearch = {
                        mainViewModel.navigateToTab(
                            navController,
                            Route.SearchScreen.route
                        )
                    }
                )
            }

            composable(route = Route.DetailCoinScreen.route) {
                val viewModel: DetailCoinViewModel = hiltViewModel()
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<String>("coinId")
                    ?.let { coinId ->
                        DetailCoinScreen(
                            viewModel = viewModel,
                            homeViewModel = homeViewModel,
                            coinId = coinId,
                            onBackClick = { navController.navigateUp() },
                        )
                    }
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    searchViewModel = viewModel,
                    authViewModel = authViewModel,
                    onBackClick = { navController.navigateUp() },
                    navigateToDetails = { coinId ->
                        mainViewModel.navigateToDetails(navController, coinId)
                    })
            }

            composable(route = Route.ProfileScreen.route) {
                ProfileScreen(authViewModel = authViewModel,
                    profileViewModel = profileViewModel,
                    navigateToTransaction = {
                        mainViewModel.navigateToTransaction(navController)
                    })
            }

            composable(route = Route.TransactionScreen.route) {
                TransactionScreen(
                    profileViewModel = profileViewModel,
                    onBackClick = { navController.navigateUp() },
                )
            }

            composable(route = Route.SignUpScreen.route) {
                SignUpScreen(
                    navigateToSignIn = {
                        authViewModel.email = ""
                        authViewModel.password = ""
                        navController.navigate(route = Route.LoginScreen.route)
                    },
                    authViewModel = authViewModel
                )
            }
            composable(route = Route.LoginScreen.route) {
                SignInScreen(
                    navigateToSignUp = {
                        authViewModel.email = ""
                        authViewModel.password = ""
                        navController.navigate(route = Route.SignUpScreen.route)
                    },
                    authViewModel = authViewModel
                )
            }

            composable(route = Route.AttachmentsScreen.route) {
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.let { state ->
                        val attachments = state.get<List<Attachment>>("attachments")
                        val selectedIndex = state.get<Int>("index")

                        attachments?.let { attachmentList ->
                            val attachmentViewModel: AttachmentsViewModel = hiltViewModel()
                            attachmentViewModel.setAttachmentsList(attachmentList)
                            AttachmentsScreen(
                                attachmentViewModel = attachmentViewModel,
                                index = selectedIndex ?: 0,
                                onBackClick = { navController.navigateUp() })
                        }
                    }

            }
        }
    }
}


