package com.example.crypto_app.domain.repository

import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.domain.model.CoinList
import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.model.search.SearchCoin
import com.example.crypto_app.domain.usecases.coins.SortingOrder

interface CoinsRepository {
    suspend fun getAllMarketCoins(sortingOrder: SortingOrder): CoinList
    suspend fun getCoinDetail(coinId: String): Coin
    suspend fun searchCoin(input: String): List<SearchCoin>

    //local
    suspend fun upsertUserInfo(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getUserById(userId: String): User?
}