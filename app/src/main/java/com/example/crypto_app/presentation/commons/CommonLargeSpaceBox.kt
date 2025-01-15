package com.example.crypto_app.presentation.commons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.crypto_app.util.Constants

@Composable
fun CommonLargeSpaceBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = Constants.Padding100),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}