package com.example.crypto_app.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.crypto_app.domain.model.profile.UserCoin
import com.example.crypto_app.presentation.commons.CommonLargeSpaceBox
import com.example.crypto_app.presentation.home.HomeViewModel
import com.example.crypto_app.ui.theme.LabelColor

@Composable
fun TabFavoriteContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    isLoading: Boolean = false,
    navigateToDetails: (String) -> Unit
) {
    val favoriteCoins = remember {
        viewModel.favoriteCoins
    }
    if (isLoading){
        CommonLargeSpaceBox { CircularProgressIndicator() }
    } else {
        if (favoriteCoins.isEmpty()){
            CommonLargeSpaceBox {
                Text(text = "Add coin to your favorite list", color = LabelColor)
            }
        } else {
            Column {
                favoriteCoins
                    .forEach{ coin ->
                        ItemContent(userCoin = coin, navigateToDetails = navigateToDetails)
                    }
            }
        }

    }
}