package com.example.crypto_app.presentation.chat.chat_detail

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.manager.CommonUtil
import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.usecases.chat.ChatUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class ChatDetailViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var lastVisibleMessage: DocumentSnapshot? = null
    private var allowNewMessageAdded: Boolean = false
    val currentUser = FirebaseAuth.getInstance().currentUser
    private var initialized = false
    var isLoading by mutableStateOf(false)
        private set
    var isLoadingMore by mutableStateOf(false)
        private set
    var isSendingMessage by mutableStateOf(false)
        private set
    var canLoadMoreMessage by mutableStateOf(true)
        private set
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages
    var chatChannel by mutableStateOf<ChatChannel?>(null)
        private set
    var chatTextField by mutableStateOf("")
        private set
    var showMessageSeenAndTime by mutableStateOf("")
        private set
    private val _listImageUrl = mutableStateListOf<Uri?>()
    var listImageUrl: List<Uri?> = _listImageUrl

    fun clear() {
        chatTextField = ""
        _messages.clear()
        _listImageUrl.clear()
        chatChannel = null
        allowNewMessageAdded = false
    }

    fun onChatTextFieldValueChange(value: String) {
        chatTextField = value
    }

    fun onShowMessageSeenAndTime(messageId: String) {
        showMessageSeenAndTime = if (showMessageSeenAndTime != messageId) messageId else ""
    }

    fun onImagePicked(listUri: List<Uri?>) {
        _listImageUrl.addAll(listUri)
    }

    fun getChatChannelDetail(chatChannelId: String) {
        if(initialized) return
        isLoading = true
        viewModelScope.launch {
            chatChannel = chatUseCases.getChatChannelDetail(chatChannelId)
            fetchChannelMessages(chatChannelId)
            observeChatChannelMessages()
            isLoading = false
            initialized = true
        }
    }

    fun sendMessage(context: Context, replyMessage: Message? = null) {
        if (chatTextField.trim().isEmpty()) {
            CommonUtil.showToast(context, "Don't leave chat box empty")
            return
        }
        isSendingMessage = true
        chatChannel?.let {
            viewModelScope.launch {
                val text = chatTextField
                val urlList = _listImageUrl.toList()
                chatTextField = ""
                _listImageUrl.clear()
                chatUseCases.sendMessage(
                    context = context,
                    chatChannel = it,
                    text = text,
                    mentions = emptyList(),
                    attachments = urlList.mapNotNull { it },
                    replyMessage = replyMessage,
                )
                isSendingMessage = false
            }

        }
    }

    fun fetchChannelMessages(chatChannelId: String) {
        isLoadingMore = true
        viewModelScope.launch {
            val res = chatUseCases.getChatChannelDetailMessages(chatChannelId, lastVisibleMessage)
            _messages.addAll(res.first)
            lastVisibleMessage = res.second
            canLoadMoreMessage = res.first.size == 5
            isLoadingMore = false
        }
    }

    private fun observeChatChannelMessages() {
        chatChannel?.let { chatChannelItem ->
            viewModelScope.launch {
                chatUseCases.observeChatChannelMessage(
                    chatChannelItem,
                    onMessageAdded = { onMessageAdded(it) },
                    onMessageUpdated = { onMessageUpdated(it) },
                    onMessageRemoved = { onMessageRemoved(it) },
                )
            }
        }
    }

    private fun onMessageAdded(message: Message) {
        if (!_messages.contains(message))
            _messages.add(0, message)
    }

    private fun onMessageUpdated(message: Message) {

    }

    private fun onMessageRemoved(message: Message) {

    }
}