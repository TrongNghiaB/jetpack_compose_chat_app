package com.example.crypto_app.domain.usecases.chat

import android.content.Context
import android.net.Uri
import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.chat.Mention
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.repository.ChatRepository

class SendMessage(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        context: Context,
        chatChannel: ChatChannel,
        text: String,
        attachments: List<Uri>,
        mentions: List<Mention>,
        replyMessage: Message?
    ): Result<Unit> {
        return chatRepository.sendMessage(context,chatChannel,text,mentions,attachments,replyMessage)
    }
}