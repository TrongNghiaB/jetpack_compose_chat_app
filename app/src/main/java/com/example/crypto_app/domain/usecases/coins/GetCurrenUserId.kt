package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.repository.AuthRepository

class GetCurrentUserId (private val authRepository: AuthRepository) {
    operator fun invoke(): String? = authRepository.userId()
}