package com.example.crypto_app.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.crypto_app.R
import com.example.crypto_app.presentation.search.SearchViewModel
import com.example.crypto_app.ui.theme.TextHighLightColor
import com.example.crypto_app.util.Constants

@Composable
fun SearchCoinList(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    navigateToDetails: (String) -> Unit
) {
    Column {
        viewModel.state.coinList!!.forEach { coin ->
            Row(
                modifier = modifier
                    .padding(vertical = Constants.Padding10)
                    .clickable {
                        navigateToDetails(coin.id)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = coin.thumb,
                    contentDescription = coin.name,
                    error = painterResource(id = R.drawable.ic_error),
                    placeholder = painterResource(id = R.drawable.ic_currency_bitcoin),
                    modifier = modifier.size(Constants.Padding24),
                )
                Spacer(modifier = modifier.width(Constants.Padding10))
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(text = coin.name, color = TextHighLightColor)
                    Text(
                        text = "Market Cap Rank: ${coin.marketCapRank}",
                        color = Color.Gray
                    )
                }
            }
        }
    }
}