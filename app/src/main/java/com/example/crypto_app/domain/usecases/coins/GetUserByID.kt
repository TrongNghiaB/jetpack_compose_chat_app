package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.repository.CoinsRepository

class GetUserByID (private val coinsRepository: CoinsRepository) {
    suspend operator fun invoke(userId: String): User? {
        return coinsRepository.getUserById(userId)
    }
}