package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.repository.ChatRepository

class UpdateChannelLastMessage (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatChannel: ChatChannel, latestMessage: String): Result<Unit> {
        return chatRepository.updateChannelLastMessage(chatChannel, latestMessage)
    }
}