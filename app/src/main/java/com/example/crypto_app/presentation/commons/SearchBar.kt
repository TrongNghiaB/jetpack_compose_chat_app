package com.example.crypto_app.presentation.commons

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crypto_app.R
import com.example.crypto_app.ui.theme.DarkSecondary
import com.example.crypto_app.ui.theme.LabelColor
import com.example.crypto_app.ui.theme.LightTextHighLightColor
import com.example.crypto_app.util.Constants.IconSize20

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            onClick?.invoke()
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .searchBarBorder(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (leadingContent != null) {
            leadingContent()
        }
        TextField(
            modifier = modifier
                .weight(1f),
            onValueChange = onValueChange,
            value = text,
            readOnly = readOnly,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(IconSize20),
                    tint = LightTextHighLightColor
                )
            },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = if (isSystemInDarkTheme()) DarkSecondary else Color.White.copy(alpha = 0.85f),
                unfocusedContainerColor = if (isSystemInDarkTheme()) DarkSecondary else Color.White.copy(alpha = 0.85f),
                disabledContainerColor = if (isSystemInDarkTheme()) DarkSecondary else Color.White.copy(alpha = 0.85f),
                focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            textStyle = MaterialTheme.typography.bodySmall,
            interactionSource = interactionSource
        )
        if(trailingContent != null) trailingContent()

    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.searchBarBorder() = composed {
    if (!isSystemInDarkTheme()) {
        border(width = 1.dp, color = Color.Black, shape = MaterialTheme.shapes.medium)
    } else {
        this
    }
}

@Preview
@Composable
private fun PreviewHomScreen() {
    SearchBar(text = "Search", readOnly = true, onValueChange = {}, trailingContent = {
        Icon(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = null,
            modifier = Modifier.size(IconSize20),
            tint = LabelColor
        )
    }, onSearch = {})
}