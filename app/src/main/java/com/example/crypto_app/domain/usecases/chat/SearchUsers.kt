package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.repository.ChatRepository

class SearchUsers (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(input: String): List<User> {
        return chatRepository.searchUser(input)
    }
}