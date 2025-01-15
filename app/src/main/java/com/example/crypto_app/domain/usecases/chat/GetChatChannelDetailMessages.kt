package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.repository.ChatRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

class GetChatChannelDetailMessages(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatChannelId: String,
        lastVisibleMessage: DocumentSnapshot?
    ): Pair<List<Message>, DocumentSnapshot?> {
        return chatRepository.getChatChannelsDetailMessages(chatChannelId, lastVisibleMessage)
    }
}