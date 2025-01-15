package com.example.crypto_app.domain.repository

import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.domain.model.CoinList
import com.example.crypto_app.domain.model.profile.ChatUser
import com.example.crypto_app.domain.usecases.coins.SortingOrder

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun addUserToFireStore(user: ChatUser): Result<Unit>
    fun isSignIn(): Boolean
    fun signOut()
    fun userId(): String?
    fun userEmail(): String?
}