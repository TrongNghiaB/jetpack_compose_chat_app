package com.example.crypto_app.presentation.commons

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun DismissKeyboardOnTapOutside(
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        }
    ) {
        content(Modifier)
    }
}