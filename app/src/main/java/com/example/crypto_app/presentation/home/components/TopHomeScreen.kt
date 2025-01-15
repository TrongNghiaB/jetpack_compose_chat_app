package com.example.crypto_app.presentation.home.components

import CommonTextButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.crypto_app.R
import com.example.crypto_app.ui.theme.LabelColor
import com.example.crypto_app.ui.theme.LightTextHighLightColor
import com.example.crypto_app.ui.theme.TextHighLightColor
import com.example.crypto_app.util.Constants

@Composable
fun TopHomeScreen(modifier: Modifier = Modifier) {
    val isSystemInDarkMode = isSystemInDarkTheme()
    var isShowTotalBalance by remember { mutableStateOf(true) }

    Row {
        Column {
            Row {
                Text(text = "Total Balance (USDT)", color = LabelColor)

                Spacer(modifier = modifier.width(Constants.Padding5))

                Icon(
                    painter = painterResource(
                        id = if(isShowTotalBalance)  R.drawable.arrow_drop_down else R.drawable.arrow_drop_up
                    ),
                    contentDescription = null,
                    tint = LabelColor,
                    modifier = Modifier
                        .size(Constants.IconSize20)
                        .clickable { isShowTotalBalance = !isShowTotalBalance },
                )
            }

            if (isShowTotalBalance)
                Text(
                    text = "3000.0",
                    fontSize = 30.sp,
                    color = if (isSystemInDarkMode) TextHighLightColor else  LightTextHighLightColor)
        }
        Spacer(modifier = Modifier.weight(1f))
        CommonTextButton(text = "Deposit") {}
    }
}