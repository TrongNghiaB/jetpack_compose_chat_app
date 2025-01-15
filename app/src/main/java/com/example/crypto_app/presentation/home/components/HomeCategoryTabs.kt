package com.example.crypto_app.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crypto_app.presentation.home.HomeViewModel

@Composable
fun HomeCategoryTabs(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigateToDetails: (String) -> Unit
    ) {
    Column {
        // TabRow to display the tabs
        TabTitles(viewModel = viewModel)

        ItemContent()

        // Content displayed for each tab
        when (viewModel.selectedTabIndex) {
            0 -> {
                TabFavoriteContent(
                    viewModel = viewModel,
                    isLoading = viewModel.isLoading,
                    navigateToDetails = navigateToDetails
                )
            }
            1 -> TabContent(allMarketCoins= viewModel.allMarketCoins,
                isLoading = viewModel.isLoading,
                navigateToDetails = navigateToDetails
            )
            2 -> TabContent(allMarketCoins= viewModel.gainerCoins,
                isLoading = viewModel.isLoading,
                navigateToDetails = navigateToDetails)
            3 -> TabContent(allMarketCoins= viewModel.loserCoins,
                isLoading = viewModel.isLoading,
                navigateToDetails = navigateToDetails)
        }
    }
}


//@Preview
//@Composable
//private fun PreviewHomScreen() {
//    val viewModel: HomeViewModel = hiltViewModel()
//    HomeCategoryTabs(viewModel = viewModel)
//}