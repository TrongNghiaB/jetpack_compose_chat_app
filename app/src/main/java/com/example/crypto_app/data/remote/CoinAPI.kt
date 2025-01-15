package com.example.crypto_app.data.remote

import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.domain.model.CoinList
import com.example.crypto_app.domain.model.search.SearchList
import com.example.crypto_app.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinAPI {
    @GET("coins/markets")
    suspend fun getAllMarketCoins(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("x-cg-pro-api-key") apiKey: String = API_KEY,
        @Query("per_page") totalPerPage: Int,
        @Query("order") order: String = "",
    ): CoinList

    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") coinId: String,
        @Query("x-cg-pro-api-key") apiKey: String = API_KEY
    ): Coin

    @GET("search")
    suspend fun searchCoins(
        @Query("query") input: String,
    ): SearchList
}