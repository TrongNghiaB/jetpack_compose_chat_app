package com.example.crypto_app.presentation.authentication.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.example.crypto_app.R
import com.example.crypto_app.presentation.authentication.AuthState
import com.example.crypto_app.presentation.authentication.AuthViewModel
import com.example.crypto_app.presentation.authentication.common_auth.ChangeAuthScreenText
import com.example.crypto_app.presentation.authentication.common_auth.CommonAuthButton
import com.example.crypto_app.presentation.authentication.common_auth.CommonAuthTextField
import com.example.crypto_app.presentation.commons.DismissKeyboardOnTapOutside
import com.example.crypto_app.util.ToastManager
import com.example.crypto_app.ui.theme.MainLogoColor
import com.example.crypto_app.util.Constants.FontSize15
import com.example.crypto_app.util.Constants.FontSize20
import com.example.crypto_app.util.Constants.Padding10
import com.example.crypto_app.util.Constants.Padding20
import com.example.crypto_app.util.Constants.Padding30
import com.example.crypto_app.util.Constants.Padding5

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navigateToSignUp: () -> Unit,
    authViewModel: AuthViewModel,
) {
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    LaunchedEffect(authViewModel.authState,authViewModel.errorMessage) {
        if(authViewModel.authState is AuthState.Error) {
            val authState = authViewModel.authState as AuthState.Error
            Toast.makeText(context,authState.message,Toast.LENGTH_SHORT).show()
        } else if(authViewModel.errorMessage.isNotEmpty()){
            ToastManager.showToast(context,authViewModel.errorMessage)
            authViewModel.clearErrorMessage()
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.getRememberMe(context)
    }
    DismissKeyboardOnTapOutside(modifier = modifier.fillMaxSize()){ innerModifier ->
        Column(
            modifier = innerModifier
                .fillMaxSize()
                .padding(horizontal = Padding30)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.signin_logo),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = modifier.height(Padding5))
            Text(
                "Crypto App",
                fontSize = FontSize20,
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
                onNextAction = {passwordFocusRequester.requestFocus()},
            )

            Spacer(modifier = modifier.height(Padding10))
            CommonAuthTextField(
                title = "Password",
                value = authViewModel.password,
                focusRequester = passwordFocusRequester,
                onValueChange = { authViewModel.password = it },
                isDarkMode = isDarkMode,
                isPassword = true,
            )
            Spacer(modifier = modifier.height(Padding10))

            RememberMeCheckBox(authViewModel = authViewModel)

            Spacer(modifier = modifier.height(Padding20))
            if (authViewModel.authState == AuthState.Loading)
                CircularProgressIndicator(modifier = modifier.align(Alignment.CenterHorizontally))
            else
                CommonAuthButton(textButton = "Login", onClick = { authViewModel.login(context) })

            Spacer(modifier = modifier.height(Padding20))

            ChangeAuthScreenText(
                isDarkMode = isDarkMode,
                instructionText = "Don't have an account yet? ",
                buttonNavigationText = "Register",
                onClick = navigateToSignUp
            )

        }
    }

}

@Composable
fun RememberMeCheckBox(modifier: Modifier = Modifier,authViewModel: AuthViewModel,) {
    val checkedState = authViewModel.checkBoxState
    Row (modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End){
        Text(
            text = "Remember Me",
            fontSize = FontSize15,
            fontStyle = FontStyle.Italic,
        )
        Checkbox(
            checked = checkedState,
            onCheckedChange = { value ->
                authViewModel.changeRememberMeCheckBox(value)
            }
        )
    }
}



//@Preview
//@Composable
//private fun PreviewHomScreen() {
//    SignInScreen(){}
//}