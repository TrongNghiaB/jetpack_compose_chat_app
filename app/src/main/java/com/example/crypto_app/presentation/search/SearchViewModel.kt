package com.example.crypto_app.presentation.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.usecases.chat.ChatUseCases
import com.example.crypto_app.util.DateUtil
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
) : ViewModel() {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    var state by mutableStateOf(SearchState())

    var isLoading by mutableStateOf(false)
        private set

    private var allUsersList by mutableStateOf(emptyList<User>())

    init {
        getAllUsers()
    }

    fun onSearchQueryChange(input: String) {
        state = if (input.isEmpty()) {
            state.copy(searchQuery = input, userList = allUsersList)
        } else {
            state.copy(searchQuery = input)
        }
    }

    fun searchUser() {
        isLoading = true
        viewModelScope.launch {
            state = state.copy(userList = chatUseCases.searchUser(state.searchQuery))
            isLoading = false
        }
    }

    private fun getAllUsers() {
        isLoading = true
        viewModelScope.launch {
//            allUsersList = chatUseCases.getAllUsers()
            state = state.copy(userList = allUsersList)
            isLoading = false
        }
    }

    fun updateAddedChatUsersList(user: User) {
        val addedToChatUsersList =
            if (state.addedToUsersList.contains(user)) state.addedToUsersList.filter { it != user }
            else state.addedToUsersList + user

        state = state.copy(addedToUsersList = addedToChatUsersList)
    }

    fun isUserAddedToChat(user: User): Boolean {
        return state.addedToUsersList.contains(user)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewChatChannel() {
        if(currentUser == null) return
        isLoading = true
        val currentPickedIdUsersToChat = state.addedToUsersList.map { it.id }
        val now = DateUtil.getLocalDateTimeNowToMilliseconds()
        val id = UUID.randomUUID().toString()
        val newChatChannel = ChatChannel(
            id = id,
            name = id,
            memberIds = currentPickedIdUsersToChat + currentUser.uid,
            createdAt = now,
            updatedAt = now,
            latestMessage = ""
        )
        viewModelScope.launch {
            chatUseCases.addNewChatChannel(newChatChannel)
            state = state.copy(addedToUsersList = emptyList())
            isLoading = false
        }

    }

//    fun searchCoin() {
//        isLoading = true
//        viewModelScope.launch {
//            state = state.copy(coinList = chatUseCases.searchCoin(state.searchQuery))
//            isLoading = false
//        }
//    }
}