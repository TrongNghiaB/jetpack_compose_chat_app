package com.example.crypto_app.presentation.authentication

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.model.share_pref.RememberMe
import com.example.crypto_app.domain.usecases.auth.AuthUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    var signUpResult by mutableStateOf(false)
        private set

    var authState by mutableStateOf<AuthState>(AuthState.Init)
        private set

    private fun isSignIn(): Boolean = authUseCases.isSignIn()

    var errorMessage by mutableStateOf("")
        private set
    //remember me
    var checkBoxState by mutableStateOf(false)
        private set
    private var rememberMe by mutableStateOf<RememberMe?>(null)

    //textField
    var email by mutableStateOf("")
    var password by mutableStateOf("")



    fun login(context: Context) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage = "Please don't leave email/password blank"
            return
        } else {
            authState = AuthState.Loading
            viewModelScope.launch {
                val result = authUseCases.signIn(email = email, password = password)
                if (result.isSuccess) {
                    //save userid to local


                    //remember me
                    authState = AuthState.Authenticated
                    if (checkBoxState && rememberMe?.email != email) {
                        val rememberMe = RememberMe(email, password)
                        val stringConvertedInfo = Gson().toJson(rememberMe)
                        authUseCases.saveRememberMe(context, stringConvertedInfo)
                    } else {
                        authUseCases.clearRememberMe(context)
                    }
                } else {
                    authState = AuthState.Error(
                        result.exceptionOrNull()?.message ?: "Can not sign in due to some error"
                    )
                }
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            signUpResult = authUseCases.signUp(email = email, password = password).isSuccess
        }
    }

    fun signOut() {
        authUseCases.signOut()
        email = ""
        password = ""
        authState = AuthState.UnAuthenticated
    }

    fun clearErrorMessage() {
        errorMessage = ""
    }

    fun changeRememberMeCheckBox(value: Boolean) {
        checkBoxState = value
    }

    fun getRememberMe(context: Context) {

        val localRememberMe = authUseCases.getRememberMe(context)
        val currentRememberMe = localRememberMe?.let {
            Gson().fromJson(it, RememberMe::class.java)
        }
        currentRememberMe?.let {
            checkBoxState = true
            email = it.email
            password = it.password
            rememberMe = RememberMe(it.email,it.password)
        }
    }
}

sealed class AuthState {
    data object Init : AuthState()
    data object Authenticated : AuthState()
    data object UnAuthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}