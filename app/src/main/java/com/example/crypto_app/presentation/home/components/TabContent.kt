package com.example.crypto_app.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.crypto_app.domain.model.CoinList
import com.example.crypto_app.presentation.commons.CommonLargeSpaceBox
import com.example.crypto_app.ui.theme.LabelColor

@Composable
fun TabContent(
    modifier: Modifier = Modifier,
    allMarketCoins: CoinList,
    isLoading: Boolean,
    navigateToDetails: (String) -> Unit
) {
    if (isLoading){
        CommonLargeSpaceBox {CircularProgressIndicator() }
    } else {
        if (allMarketCoins.isEmpty()){
            CommonLargeSpaceBox {
                Text(text = "Something went wrong. Can not load coin list", color = LabelColor)
            }
        } else {
            Column {
                allMarketCoins
                    .forEach{ coin ->
                        ItemContent(coin = coin, navigateToDetails = navigateToDetails)
                    }
            }
        }

    }
}