package com.example.crypto_app.presentation.authentication.common_auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.crypto_app.ui.theme.MainLogoColor

@Composable
fun ChangeAuthScreenText(
    modifier: Modifier = Modifier, isDarkMode:Boolean,
    instructionText: String,
    buttonNavigationText:String,
    onClick: () -> Unit
) {
    val color = if (isDarkMode) Color.White else Color.Black
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
        Text(instructionText,color = color)
        Text(
            buttonNavigationText,
            color = MainLogoColor,
            modifier = modifier.clickable { onClick() })
    }
}