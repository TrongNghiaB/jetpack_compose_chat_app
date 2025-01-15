package com.example.crypto_app.presentation.search

import com.example.crypto_app.domain.model.profile.ChatUser
import com.example.crypto_app.domain.model.search.SearchCoin

data class SearchState(
    var searchQuery: String = "",
    var coinList: List<SearchCoin>? = null,
    var userList: List<ChatUser>? = null,
    var addedToChatUsersList: List<ChatUser> = emptyList(),
    var searchHistory: List<String> = emptyList()
)