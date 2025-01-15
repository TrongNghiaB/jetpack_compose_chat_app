package com.example.crypto_app.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.crypto_app.R
import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.ui.theme.LightTextHighLightColor
import com.example.crypto_app.ui.theme.TabSelectColor
import com.example.crypto_app.ui.theme.TextHighLightColor
import com.example.crypto_app.util.Constants

@Composable
fun CoinDetailHeader(modifier: Modifier = Modifier, coin: Coin, isSystemInDarkMode: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = coin.image.small,
            contentDescription = coin.name,
            error = painterResource(id = R.drawable.ic_error),
            placeholder = painterResource(id = R.drawable.ic_currency_bitcoin),
            modifier = modifier.size(Constants.Padding30),
        )
        Spacer(modifier = modifier.width(Constants.Padding5))
        Text(
            text = coin.name,
            fontSize = Constants.FontSize20,
            color = if (isSystemInDarkMode) TextHighLightColor else LightTextHighLightColor
        )
        Spacer(modifier = modifier.width(Constants.Padding5))
        Text(
            text = "Price ${coin.symbol.uppercase()}",
            fontSize = Constants.FontSize20,
            color = Color.Gray
        )
        Spacer(modifier = modifier.width(Constants.Padding5))

        Text(
            modifier = modifier
                .background(
                    color = TabSelectColor,
                    shape = RoundedCornerShape(Constants.Padding10)
                )
                .padding(Constants.Padding5),
            text = "#${coin.marketCapRank}",
            fontSize = Constants.FontSize15,
            color = Color.White
        )
    }
}