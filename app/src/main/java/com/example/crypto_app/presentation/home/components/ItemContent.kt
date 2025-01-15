package com.example.crypto_app.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.crypto_app.R
import com.example.crypto_app.domain.model.CoinItem
import com.example.crypto_app.domain.model.profile.UserCoin
import com.example.crypto_app.ui.theme.GreenPrimary
import com.example.crypto_app.ui.theme.LightTextHighLightColor
import com.example.crypto_app.ui.theme.RedPrimary
import com.example.crypto_app.ui.theme.TabSelectColor
import com.example.crypto_app.ui.theme.TextHighLightColor
import com.example.crypto_app.util.Constants
import com.example.crypto_app.util.Constants.Padding24
import com.example.crypto_app.util.Constants.Padding5

@Composable
fun ItemContent(
    modifier: Modifier = Modifier,
    coin: CoinItem? = null,
    userCoin: UserCoin? = null,
    navigateToDetails: ((String) -> Unit)? = null) {
    val isSystemInDarkMode = isSystemInDarkTheme()
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Constants.Padding10)
            .clickable {
                if (navigateToDetails != null && coin?.id != null) {
                    navigateToDetails(coin.id)
                } else if (navigateToDetails != null && userCoin?.id != null) {
                    navigateToDetails(userCoin.id)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (userCoin != null) {
            AsyncImage(
                model = userCoin.image,
                contentDescription = userCoin.name,
                error = painterResource(id = R.drawable.ic_error),
                placeholder = painterResource(id = R.drawable.ic_currency_bitcoin),
                modifier = modifier.size(Padding24),
            )
            Spacer(modifier = modifier.width(Padding5))
            Column (verticalArrangement = Arrangement.SpaceBetween){
                Text(
                    text = userCoin.symbol.uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkMode) TextHighLightColor else LightTextHighLightColor,
                )
                Text(
                    text = userCoin.name.uppercase(),
                    fontSize = 15.sp,
                    color = if (isSystemInDarkMode) TextHighLightColor else LightTextHighLightColor,
                )
            }
        } else {
            if (coin == null) {
                Text(
                    text = "Name",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkMode) Color.Gray else Color.Black,
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "Last Price",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkMode) Color.Gray else Color.Black,
                )
                Spacer(modifier = modifier.width(Constants.Padding15))
                Text(
                    text = "24h chg%",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkMode) Color.Gray else Color.Black,
                )
            } else {
                AsyncImage(
                    model = coin.image,
                    contentDescription = coin.name,
                    error = painterResource(id = R.drawable.ic_error),
                    placeholder = painterResource(id = R.drawable.ic_currency_bitcoin),
                    modifier = modifier.size(Padding24),
                )
                Spacer(modifier = modifier.width(Padding5))
                Text(
                    text = coin.symbol.uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkMode) TextHighLightColor else LightTextHighLightColor,
                )
                Spacer(modifier = modifier.weight(1f))
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${coin.currentPrice}",
                        color = if (isSystemInDarkMode) TextHighLightColor else LightTextHighLightColor,
                    )
                    Text(
                        text = "${coin.currentPrice}$",
                        color = TabSelectColor
                    )
                }
                Spacer(modifier = modifier.width(Constants.Padding10))
                Box(
                    modifier = modifier
                        .background(
                            color = if (coin.priceChange24h >= 0.0) GreenPrimary else RedPrimary,
                            shape = RoundedCornerShape(Constants.Padding5)
                        )
                        .width(Constants.Padding75)
                        .height(Constants.Padding30),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = coin.percentage24hDifference(),
                        color = if (isSystemInDarkMode) TextHighLightColor else LightTextHighLightColor,
                        modifier = modifier.padding(Constants.Padding5)
                    )
                }
            }
    }
    }
}