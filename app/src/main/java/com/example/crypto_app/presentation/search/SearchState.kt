package com.example.crypto_app.presentation.search

import com.example.crypto_app.domain.model.profile.User

data class SearchState(
    var searchQuery: String = "",
    var userList: List<User>? = null,
    var addedToUsersList: List<User> = emptyList(),
    var searchHistory: List<String> = emptyList()
)