package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.domain.repository.CoinsRepository

class GetCoinDetail (private val coinsRepository: CoinsRepository) {
    suspend operator fun invoke(coinId: String): Coin {
        return coinsRepository.getCoinDetail(coinId)
    }
}