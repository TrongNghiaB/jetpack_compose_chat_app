package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.model.CoinList
import com.example.crypto_app.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow

class GetAllMarketCoins (private val coinsRepository: CoinsRepository) {
    suspend operator fun invoke(sortingOrder: SortingOrder): CoinList{
        return coinsRepository.getAllMarketCoins( sortingOrder)
    }
}

sealed class SortingOrder(val order: String) {
    object None: SortingOrder("")
    object MarketCapAsc : SortingOrder("market_cap_asc")
    object MarketCapDesc : SortingOrder("market_cap_desc")
    object VolumeAsc : SortingOrder("volume_asc")
    object VolumeDesc : SortingOrder("volume_desc")
}
