package com.example.crypto_app.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CommonRoundIconButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    icon: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = backgroundColor ?: Color.LightGray.copy(alpha = 0.5f),
                shape = CircleShape
            )
    ) {
        icon()
    }
}