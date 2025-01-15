package com.example.crypto_app.domain.usecases.auth

import com.example.crypto_app.domain.repository.AuthRepository

class SignUp (private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit>{
        return authRepository.signUp(email = email, password = password)
    }
}