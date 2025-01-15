package com.example.crypto_app.presentation.chat

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.manager.CommonUtil
import com.example.crypto_app.domain.manager.FirebaseStorageHelper
import com.example.crypto_app.domain.manager.toChatChannel
import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.chat.Mention
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.usecases.chat.ChatUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
) : ViewModel() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val tabs = listOf("HOME", "CHANNELS")
    var isLoading by mutableStateOf(false)
        private set
    var selectedTabIndex by mutableIntStateOf(0)
        private set
    var chatChannels by mutableStateOf<List<ChatChannel>>(emptyList())
        private set
    var chatChannel by mutableStateOf<ChatChannel?>(null)
        private set

    init {
        observeChatChannels()
    }

    fun changeChatTab(index: Int) {
        selectedTabIndex = index
    }


    private fun observeChatChannels() {
        viewModelScope.launch {
            chatUseCases.getChatChannels().collect { snapshot ->
                snapshot.let {
                    chatChannels = it.documents.map { doc -> doc.toChatChannel() }
                }
            }
        }
    }
}