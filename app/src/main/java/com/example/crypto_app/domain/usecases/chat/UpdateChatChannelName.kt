package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.repository.ChatRepository


class UpdateChatChannelName (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatChannel: ChatChannel,name: String): Result<Unit> {
        return chatRepository.updateChatChannelName(chatChannel,name)
    }
}