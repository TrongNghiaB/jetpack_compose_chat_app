package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.repository.ChatRepository
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

class ObserveChatChannelMessage(private val chatRepository: ChatRepository) {
    operator fun invoke(
        chatChannel: ChatChannel,
        onMessageAdded: (Message) -> Unit,
        onMessageUpdated: (Message) -> Unit,
        onMessageRemoved: (Message) -> Unit
    ) {
        return chatRepository.observeChatChannelMessage(
            chatChannel, onMessageAdded, onMessageUpdated, onMessageRemoved
        )
    }
}