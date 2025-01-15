package com.example.crypto_app.presentation.search.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.crypto_app.R
import com.example.crypto_app.presentation.commons.SearchBar
import com.example.crypto_app.presentation.search.SearchViewModel
import com.example.crypto_app.ui.theme.ButtonColor
import com.example.crypto_app.ui.theme.LightTextHighLightColor
import com.example.crypto_app.ui.theme.MainLogoColor
import com.example.crypto_app.util.Constants

@Composable
fun SearchScreenAppBar(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onBackClick: () -> Unit
) {
    SearchBar(
        text = viewModel.state.searchQuery,
        readOnly = false,
        onValueChange = { viewModel.onSearchQueryChange(it) },
        onSearch = { viewModel.searchUser() },
        leadingContent = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(Constants.IconSize20),
                    tint = LightTextHighLightColor
                )
            }
        },
        trailingContent = {
            TextButton(onClick = { viewModel.searchUser() }) {
                Text("Search", color = MainLogoColor)
            }
        }
    )
}