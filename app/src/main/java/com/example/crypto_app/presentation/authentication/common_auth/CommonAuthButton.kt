package com.example.crypto_app.presentation.authentication.common_auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.crypto_app.ui.theme.MainLogoColor
import com.example.crypto_app.util.Constants

@Composable
fun CommonAuthButton(modifier: Modifier = Modifier,textButton: String, onClick : () -> Unit){
    Box(modifier = modifier.fillMaxWidth()){
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(Constants.Padding10),
            border = BorderStroke(Constants.Padding1, color = MainLogoColor),
            modifier = modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Center),
            contentPadding = PaddingValues(vertical = Constants.Padding5)
        ) {
            Text(text = textButton, color = MainLogoColor)
        }
    }
}