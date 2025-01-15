package com.example.crypto_app.presentation.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.crypto_app.R
import com.example.crypto_app.ui.theme.ButtonColor
import com.example.crypto_app.ui.theme.LightTextHighLightColor
import com.example.crypto_app.ui.theme.TextHighLightColor
import com.example.crypto_app.util.Constants.Padding1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    title: @Composable () -> Unit = {},
    backButtonColor: Color? = null,
    containerColor: Color? = null,
    actions: @Composable() (RowScope.() -> Unit) = {}) {
    val isInDarkMode = isSystemInDarkTheme()
    Column {
        TopAppBar(
            title = title,
            modifier = modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = containerColor ?: Color.Transparent,
                navigationIconContentColor = colorResource(id = R.color.white),
            ),
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = null,
                        tint = backButtonColor ?: Color.Black
                    )
                }
            },
            actions = actions,
        )

        HorizontalDivider(
            thickness = Padding1,
            color = if(isInDarkMode) TextHighLightColor else LightTextHighLightColor )
    }
}