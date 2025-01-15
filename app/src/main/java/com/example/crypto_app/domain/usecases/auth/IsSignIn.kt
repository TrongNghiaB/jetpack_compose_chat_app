package com.example.crypto_app.domain.usecases.auth

import com.example.crypto_app.domain.repository.AuthRepository

class IsSignIn(private val authRepository: AuthRepository) {
     operator fun invoke(): Boolean = authRepository.isSignIn()
}