package com.example.crypto_app.domain.model.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatChannel(
    val id: String,
    val name: String,
    val memberIds: List<String>,
    val createdAt: Long,
    val updatedAt: Long,
    val latestMessage: String,
) : Parcelable