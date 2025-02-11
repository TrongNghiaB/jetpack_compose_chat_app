package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.repository.ChatRepository
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

class GetChatChannels (private val chatRepository: ChatRepository) {
    operator fun invoke(): Flow<QuerySnapshot> {
        return chatRepository.getChatChannels()
    }
}