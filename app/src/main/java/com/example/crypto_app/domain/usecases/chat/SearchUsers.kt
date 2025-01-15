package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.profile.ChatUser
import com.example.crypto_app.domain.repository.ChatRepository

class SearchUsers (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(input: String): List<ChatUser> {
        return chatRepository.searchUser(input)
    }
}