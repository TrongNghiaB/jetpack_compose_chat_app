package com.example.crypto_app.domain.usecases.coins
import com.example.crypto_app.domain.model.profile.ChatUser
import com.example.crypto_app.domain.repository.ChatRepository

class GetAllUsers (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(): List<ChatUser> {
        return chatRepository.getAllUsers()
    }
}