package com.example.crypto_app.presentation.authentication.common_auth

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.crypto_app.R
import com.example.crypto_app.ui.theme.DarkSecondary
import com.example.crypto_app.util.Constants

@Composable
fun CommonAuthTextField(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = true,
    title: String,
    value: String = "",
    onValueChange: ((String) -> Unit) = {},
    isPassword: Boolean = false,
    focusRequester: FocusRequester = remember { FocusRequester() },
    imeAction: ImeAction = ImeAction.Done,
    onNextAction: () -> Unit = {},
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions? = null,
    titleColor: Color? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
) {
    val isViewPassword = remember {
        mutableStateOf(!isPassword)
    }

    Column {
        Text(
            text = title,
            fontSize = Constants.FontSize15,
            color = titleColor ?: if (isDarkMode) Color.White else Color.Black
        )
        Spacer(modifier = modifier.height(Constants.Padding5))
        TextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            readOnly = readOnly,
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.DarkGray,
                focusedContainerColor = Color.DarkGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            ),
            shape = RoundedCornerShape(Constants.Padding10),
            visualTransformation = if (isViewPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = leadingIcon,
            trailingIcon =
            if (isPassword) {
                {
                    IconButton(onClick = { isViewPassword.value = !isViewPassword.value }) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (isViewPassword.value)
                                    R.drawable.ic_obscure else R.drawable.ic_visibility_off
                            ),
                            tint = if (isDarkMode) Color.White else Color.Black,
                            contentDescription = null
                        )
                    }
                }
            } else {
                trailingIcon
            },
            keyboardOptions = keyboardOptions ?: KeyboardOptions.Default.copy(
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { onNextAction() }
            ),
        )
    }

}