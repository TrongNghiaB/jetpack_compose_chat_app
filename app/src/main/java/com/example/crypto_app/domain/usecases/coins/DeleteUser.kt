package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.repository.CoinsRepository

class DeleteUser (private val coinsRepository: CoinsRepository) {
    suspend operator fun invoke(user: User) {
        coinsRepository.deleteUser(user)
    }
}