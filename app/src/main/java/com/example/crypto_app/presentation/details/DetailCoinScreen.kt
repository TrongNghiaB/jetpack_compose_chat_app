package com.example.crypto_app.presentation.details

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.crypto_app.domain.model.profile.UserCoin
import com.example.crypto_app.presentation.commons.CommonAppBar
import com.example.crypto_app.presentation.details.components.CoinDetailCurrentPriceAndPercentage
import com.example.crypto_app.presentation.details.components.CoinDetailDescriptionAndCategories
import com.example.crypto_app.presentation.details.components.CoinDetailHeader
import com.example.crypto_app.presentation.details.components.DetailCoinInformation
import com.example.crypto_app.presentation.details.components.LoadCoinAgain
import com.example.crypto_app.presentation.home.HomeViewModel
import com.example.crypto_app.ui.theme.ButtonColor
import com.example.crypto_app.util.Constants.Padding1
import com.example.crypto_app.util.Constants.Padding10
import com.example.crypto_app.util.Constants.Padding15
import com.example.crypto_app.util.Constants.Padding20
import com.example.crypto_app.util.Constants.Padding500

@Composable
fun DetailCoinScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailCoinViewModel,
    homeViewModel: HomeViewModel,
    coinId: String,
    onBackClick: () -> Unit
) {
    val isSystemInDarkMode = isSystemInDarkTheme()
    LaunchedEffect(Unit) {
        viewModel.getCoinDetail(coinId)
    }
    val favoriteCoins =  remember {
        homeViewModel.favoriteCoins
    }

    if (viewModel.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (viewModel.coin == null) {
            LoadCoinAgain(viewModel = viewModel, coinId = coinId)
        } else {
            val coin = viewModel.coin!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = Padding10)
                    .verticalScroll(rememberScrollState())
            ) {
                CommonAppBar(
                    onBackClick = onBackClick,
                    actions = {
                        val userCoin = UserCoin(
                            id = coin.id,
                            name = coin.name,
                            symbol = coin.symbol,
                            image = coin.image.thumb)
                        IconButton(onClick = {
                            homeViewModel.updateListCoinFavorites(userCoin)
                        }) {
                            Icon(
                                painter = painterResource(id = androidx.appcompat.R.drawable.abc_star_black_48dp),
                                tint = if(favoriteCoins.contains(userCoin))
                                        ButtonColor else Color.White,
                                contentDescription = null
                            )
                        }
                    })

                Spacer(modifier = modifier.height(Padding10))

                CoinDetailHeader(coin = coin, isSystemInDarkMode = isSystemInDarkMode)

                Spacer(modifier = modifier.height(Padding15))

                CoinDetailCurrentPriceAndPercentage(coin = coin, isSystemInDarkMode = isSystemInDarkMode)

                Spacer(modifier = modifier.height(Padding10))

                CoinDetailDescriptionAndCategories(coin = coin)

                Spacer(modifier = modifier.height(Padding10))

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(Padding500)
                        .border(width = Padding1, color = Color.Red)
                )

                Spacer(modifier = modifier.height(Padding20))

                DetailCoinInformation(coin = coin)
            }
        }
    }
}
