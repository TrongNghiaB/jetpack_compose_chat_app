package com.example.crypto_app.domain.usecases.chat

import com.example.crypto_app.domain.usecases.coins.GetAllUsers

data class ChatUseCases(
    val searchUser: SearchUsers,
    val getAllUsers: GetAllUsers,
    val addNewChatChannel: AddNewChatChannel,
    val getChatChannels: GetChatChannels,
    val sendMessage: SendMessage,
    val updateChatChannelName: UpdateChatChannelName,
    val updateChannelLastMessage: UpdateChannelLastMessage,
    val getChatChannelDetail: GetChatChannelDetail,
    val getChatChannelDetailMessages: GetChatChannelDetailMessages,
    val observeChatChannelMessage: ObserveChatChannelMessage,
)