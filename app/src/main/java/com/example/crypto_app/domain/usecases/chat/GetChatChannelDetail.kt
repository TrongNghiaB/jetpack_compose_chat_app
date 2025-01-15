package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.repository.ChatRepository
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

class GetChatChannelDetail (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatChannelId:String): ChatChannel? {
        return chatRepository.getChatChannelsDetail(chatChannelId)
    }
}