package com.example.crypto_app.data.repository

import com.example.crypto_app.data.local.CryptoDao
import com.example.crypto_app.data.remote.CoinAPI
import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.domain.model.CoinList
import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.model.search.SearchCoin
import com.example.crypto_app.domain.repository.CoinsRepository
import com.example.crypto_app.domain.usecases.coins.SortingOrder

class CoinRepositoryImpl(
    private val coinApi: CoinAPI,
    private val cryptoDao: CryptoDao,
): CoinsRepository {
    override suspend fun getAllMarketCoins(sortingOrder: SortingOrder): CoinList {
        return coinApi.getAllMarketCoins(totalPerPage = 10, order = sortingOrder.order)
    }

    override suspend fun getCoinDetail(coinId: String): Coin {
        return coinApi.getCoinDetail(coinId)
    }

    override suspend fun searchCoin(input: String): List<SearchCoin> {
        return coinApi.searchCoins(input).searchCoins
    }

    override suspend fun upsertUserInfo(user: User) {
        cryptoDao.upsertUserInfo(user)
    }

    override suspend fun deleteUser(user: User) {
        cryptoDao.deleteUser(user)
    }

    override suspend fun getUserById(userId: String): User? {
        return cryptoDao.getUserById(userId)
    }
}