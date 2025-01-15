package com.example.crypto_app.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.ui.theme.TabSelectColor
import com.example.crypto_app.util.Constants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailDescriptionAndCategories(modifier: Modifier = Modifier, coin: Coin) {
    Text(
        text = coin.description.en,
        color = TabSelectColor,
        fontSize = Constants.FontSize20,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
    Spacer(modifier = modifier.height(Constants.Padding10))

    Text(
        text = "Categories",
        fontSize = Constants.FontSize20,
        color = Color.White
    )

    Spacer(modifier = modifier.height(Constants.Padding5))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Constants.Padding10),
        verticalArrangement = Arrangement.spacedBy(Constants.Padding10),
    ) {
        coin.categories.forEachIndexed { _, category ->
            Text(
                modifier = modifier
                    .background(
                        color = TabSelectColor,
                        shape = RoundedCornerShape(Constants.Padding10)
                    )
                    .padding(Constants.Padding5),
                text = category,
                fontSize = Constants.FontSize20,
                color = Color.White
            )
        }
    }
}