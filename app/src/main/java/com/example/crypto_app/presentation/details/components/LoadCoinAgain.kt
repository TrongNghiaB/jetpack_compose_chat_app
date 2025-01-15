package com.example.crypto_app.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.crypto_app.R
import com.example.crypto_app.presentation.details.DetailCoinViewModel

@Composable
fun LoadCoinAgain(modifier: Modifier = Modifier, viewModel: DetailCoinViewModel, coinId: String) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { viewModel.getCoinDetail(coinId) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_reload),
                contentDescription = null
            )
        }
        Text(
            "Can get coin data.\n Press load icon to load again",
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}