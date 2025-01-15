package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.repository.AuthRepository

class GetCurrentUserEmail (private val authRepository: AuthRepository) {
    operator fun invoke(): String? = authRepository.userEmail()
}