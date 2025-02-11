package com.example.crypto_app.domain.repository

import com.example.crypto_app.domain.model.profile.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun addUserToFireStore(user: User): Result<Unit>
    fun isSignIn(): Boolean
    fun signOut()
    fun userId(): String?
    fun userEmail(): String?
}