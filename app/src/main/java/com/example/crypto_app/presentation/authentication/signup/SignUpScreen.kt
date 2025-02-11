package com.example.crypto_app.presentation.authentication.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.example.crypto_app.R
import com.example.crypto_app.presentation.authentication.AuthState
import com.example.crypto_app.presentation.authentication.AuthViewModel
import com.example.crypto_app.presentation.authentication.common_auth.ChangeAuthScreenText
import com.example.crypto_app.presentation.authentication.common_auth.CommonAuthButton
import com.example.crypto_app.presentation.authentication.common_auth.CommonAuthTextField
import com.example.crypto_app.presentation.commons.DismissKeyboardOnTapOutside
import com.example.crypto_app.ui.theme.MainLogoColor
import com.example.crypto_app.util.Constants

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navigateToSignIn: () -> Unit,
    authViewModel: AuthViewModel,
) {
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    LaunchedEffect(authViewModel.signUpResult) {
        if (authViewModel.signUpResult) navigateToSignIn()
    }

    val isDarkMode = isSystemInDarkTheme()
    DismissKeyboardOnTapOutside(modifier = modifier.fillMaxSize()) { innerModifier ->
        Column(
            modifier = innerModifier
                .fillMaxSize()
                .padding(horizontal = Constants.Padding30)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.signin_logo),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = modifier.height(Constants.Padding5))
            Text(
                "Crypto App",
                fontSize = Constants.FontSize20,
                color = MainLogoColor,
                fontWeight = FontWeight.W600,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )

            CommonAuthTextField(
                title = "Email",
                value = authViewModel.email,
                focusRequester = emailFocusRequester,
                onValueChange = { authViewModel.email = it },
                isDarkMode = isDarkMode,
                imeAction = ImeAction.Next,
                onNextAction = { passwordFocusRequester.requestFocus() },
            )

            Spacer(modifier = modifier.height(Constants.Padding10))
            CommonAuthTextField(
                title = "Password",
                isDarkMode = isDarkMode,
                value = authViewModel.password,
                focusRequester = passwordFocusRequester,
                onValueChange = { authViewModel.password = it },
                isPassword = true,
            )

            Spacer(modifier = modifier.height(Constants.Padding20))

            if (authViewModel.authState == AuthState.Loading)
                CircularProgressIndicator(modifier = modifier.align(Alignment.CenterHorizontally))
            else
                CommonAuthButton(
                    textButton = "Sign Up",
                    onClick = { authViewModel.signUp() })

            Spacer(modifier = modifier.height(Constants.Padding20))

            ChangeAuthScreenText(
                isDarkMode = isDarkMode,
                instructionText = "Already have an account? ",
                buttonNavigationText = "Sign In",
                onClick = navigateToSignIn
            )
        }
    }
}


//@Preview
//@Composable
//private fun PreviewHomScreen() {
//    SignUpScreen() {}
//}