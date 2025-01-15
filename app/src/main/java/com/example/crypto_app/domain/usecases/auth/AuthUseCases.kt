package com.example.crypto_app.domain.usecases.auth

import com.example.crypto_app.domain.usecases.coins.GetCurrentUserEmail
import com.example.crypto_app.domain.usecases.coins.GetCurrentUserId

data class AuthUseCases (
    val signIn: SignIn,
    val signUp: SignUp,
    val isSignIn: IsSignIn,
    val signOut: SignOut,
    val getRememberMe: GetRememberMe,
    val saveRememberMe: SaveRememberMe,
    val clearRememberMe: ClearRememberMe,
    val getCurrentUserId: GetCurrentUserId,
    val getCurrentUserEmail: GetCurrentUserEmail
)