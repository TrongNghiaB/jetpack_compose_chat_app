package com.example.crypto_app.presentation.commons

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.example.crypto_app.ui.theme.DarkSecondary
import com.example.crypto_app.util.Constants

@Composable
fun KeyAndValueField(
    modifier: Modifier = Modifier,
    isSystemInDarkMode: Boolean = true,
    keyText: String,
    isLink: Boolean = false,
    hasDivider: Boolean = true,
    valueText: String = ""
) {
    val context = LocalContext.current


    Row {
        Text(
            text = keyText,
            fontSize = Constants.FontSize15,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isSystemInDarkMode) Color.LightGray else Color.DarkGray
        )
        Spacer(modifier = modifier.weight(1f))

        Text(
            valueText,
            fontSize = Constants.FontSize15,
            maxLines = 1,
            style = TextStyle(textDecoration = if (isLink) TextDecoration.Underline else null),
            overflow = TextOverflow.Ellipsis,
            color = if (isSystemInDarkMode) Color.White else Color.Black,
            modifier = modifier.clickable {
                if (isLink) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(valueText))
                    context.startActivity(intent)
                }
            })
    }
    if (hasDivider) {
        HorizontalDivider(
            modifier = modifier.padding(vertical = Constants.Padding10),
            thickness = Constants.Padding3,
            color = DarkSecondary
        )
    }
}