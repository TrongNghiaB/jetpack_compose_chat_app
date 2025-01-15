package com.example.crypto_app.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.crypto_app.R
import com.example.crypto_app.presentation.commons.PullToRefreshLazyColumn
import com.example.crypto_app.presentation.commons.SearchBar
import com.example.crypto_app.presentation.home.components.HomeCategoryTabs
import com.example.crypto_app.presentation.home.components.TopHomeScreen
import com.example.crypto_app.ui.theme.LabelColor
import com.example.crypto_app.util.Constants
import com.example.crypto_app.util.Constants.Padding20

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigateToSearch: () -> Unit,
    navigateToDetails: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getAllMarketCoins(0)
    }

    PullToRefreshLazyColumn(
        isRefreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refreshHome() },
        content = {
            SearchBar(
                text = "",
                readOnly = true,
                onValueChange = {},
                onClick = {
                    navigateToSearch()
                },
                onSearch = {},
                trailingContent = {
                    IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = null,
                        modifier = Modifier.size(Constants.IconSize20),
                        tint = LabelColor
                    )}
                }
            )

            Spacer(modifier = modifier.height(Padding20))

            TopHomeScreen()

            Spacer(modifier = modifier.height(Padding20))

            HomeCategoryTabs(viewModel = viewModel, navigateToDetails = navigateToDetails)
        },
    )

}


//@Preview
//@Composable
//private fun PreviewHomScreen() {
//    val viewModel: HomeViewModel = hiltViewModel()
//    HomeScreen(viewModel = viewModel)
//}