package com.example.crypto_app.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.crypto_app.presentation.home.HomeViewModel
import com.example.crypto_app.ui.theme.TabSelectColor
import com.example.crypto_app.util.Constants

@Composable
fun TabTitles(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {
        viewModel.homeTabs.forEachIndexed { index, title ->
            Box(
                modifier = Modifier
                    .clickable { viewModel.changeTab(index) }
                    .background(
                        color = if (viewModel.selectedTabIndex == index) TabSelectColor else Color.Transparent,
                        shape = RoundedCornerShape(Constants.Padding5)
                    )
                    .padding(horizontal = Constants.Padding10),

                contentAlignment = Alignment.Center,

                ) {
                Text(
                    text = title,
                    color = if (viewModel.selectedTabIndex == index) Color.White else Color.Gray,
                    modifier = Modifier.padding(Constants.Padding5)
                )
            }
        }
    }
}