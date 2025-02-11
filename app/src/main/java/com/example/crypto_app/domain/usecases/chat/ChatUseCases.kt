package com.example.crypto_app.domain.usecases.chat

data class ChatUseCases(
    val searchUser: SearchUsers,
    val addNewChatChannel: AddNewChatChannel,
    val getChatChannels: GetChatChannels,
    val sendMessage: SendMessage,
    val updateChatChannelName: UpdateChatChannelName,
    val updateChannelLastMessage: UpdateChannelLastMessage,
    val getChatChannelDetail: GetChatChannelDetail,
    val getChatChannelDetailMessages: GetChatChannelDetailMessages,
    val observeChatChannelMessage: ObserveChatChannelMessage,
)