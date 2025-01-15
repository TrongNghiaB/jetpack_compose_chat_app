package com.example.crypto_app.domain.usecases.auth

import com.example.crypto_app.domain.repository.AuthRepository

class SignOut (private val authRepository: AuthRepository) {
    operator fun invoke(){
        return authRepository.signOut()
    }
}