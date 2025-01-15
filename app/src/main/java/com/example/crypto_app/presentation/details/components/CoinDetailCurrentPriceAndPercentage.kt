package com.example.crypto_app.presentation.details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.crypto_app.R
import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.ui.theme.GreenPrimary
import com.example.crypto_app.ui.theme.LightTextHighLightColor
import com.example.crypto_app.ui.theme.RedPrimary
import com.example.crypto_app.ui.theme.TextHighLightColor
import com.example.crypto_app.util.Constants

@Composable
fun CoinDetailCurrentPriceAndPercentage(modifier: Modifier = Modifier, coin: Coin, isSystemInDarkMode: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "${coin.marketData.currentPrice["usd"]} US$",
            fontSize = Constants.FontSize40,
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkMode) TextHighLightColor else LightTextHighLightColor
        )
        Icon(
            painter = painterResource(
                id = if (coin.marketData.priceChange24H >= 0.0)
                    R.drawable.arrow_drop_up else
                    R.drawable.arrow_drop_down
            ),
            contentDescription = null,
            tint =  if (coin.marketData.priceChange24H >= 0.0)
                GreenPrimary else RedPrimary,
            modifier = Modifier
                .size(Constants.IconSize25)
        )
        Text(
            text = coin.marketData.percentage24hDifference(),
            fontSize = Constants.FontSize20,
            color =  if (coin.marketData.priceChange24H >= 0.0)
                GreenPrimary else RedPrimary,
        )
    }
}