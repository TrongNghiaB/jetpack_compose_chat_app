package com.example.crypto_app.domain.usecases.auth

data class AuthUseCases (
    val signIn: SignIn,
    val signUp: SignUp,
    val isSignIn: IsSignIn,
    val signOut: SignOut,
    val getRememberMe: GetRememberMe,
    val saveRememberMe: SaveRememberMe,
    val clearRememberMe: ClearRememberMe,
)