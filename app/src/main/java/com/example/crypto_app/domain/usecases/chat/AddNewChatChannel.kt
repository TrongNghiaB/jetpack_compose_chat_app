package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.repository.ChatRepository

class AddNewChatChannel (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatChannel: ChatChannel): Result<Unit> {
        return chatRepository.addNewChatChannel(chatChannel)
    }
}