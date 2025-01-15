package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.model.search.SearchCoin
import com.example.crypto_app.domain.repository.CoinsRepository

class SearchCoins (private val coinsRepository: CoinsRepository) {
    suspend operator fun invoke(input: String): List<SearchCoin> {
        return coinsRepository.searchCoin(input)
    }
}