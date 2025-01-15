package com.example.crypto_app.domain.usecases.coins

import com.example.crypto_app.domain.usecases.chat.AddNewChatChannel
import com.example.crypto_app.domain.usecases.chat.SearchUsers

data class CoinsUseCases(
    val getAllMarketCoins: GetAllMarketCoins,
    val getCoinDetail: GetCoinDetail,
    val searchCoin: SearchCoins,
    val upsertUserInfo: UpsertUserInfo,
    val deleteUser: DeleteUser,
    val getUserByID: GetUserByID,
)