package com.example.crypto_app.domain.repository

import android.content.Context
import android.net.Uri
import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.chat.Mention
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.model.profile.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun searchUser(input: String): List<User>
    suspend fun getAllUsers(): List<User>
    suspend fun addNewChatChannel(chatChannel: ChatChannel): Result<Unit>
    suspend fun updateChatChannelName(
        chatChannel: ChatChannel,
        name: String,
        now: Long? = null
    ): Result<Unit>

    suspend fun updateChannelLastMessage(
        chatChannel: ChatChannel,
        latestMessage: String,
        now: Long? = null,
    ): Result<Unit>

    fun getChatChannels(): Flow<QuerySnapshot>
    fun observeChatChannelMessage(
        chatChannel: ChatChannel,
        onMessageAdded: (Message) -> Unit,
        onMessageUpdated: (Message) -> Unit,
        onMessageRemoved: (Message) -> Unit
    )

    suspend fun getChatChannelsDetail(chatChannelId: String): ChatChannel?
    suspend fun getChatChannelsDetailMessages(
        chatChannelId: String,
        lastVisibleMessage: DocumentSnapshot? = null
    ): Pair<List<Message>, DocumentSnapshot?>

    suspend fun sendMessage(
        context: Context,
        chatChannel: ChatChannel,
        text: String,
        mentions: List<Mention>,
        attachments: List<Uri>,
        replyMessage: Message? = null
    ): Result<Unit>
}
