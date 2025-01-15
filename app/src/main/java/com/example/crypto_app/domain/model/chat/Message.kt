package com.example.crypto_app.domain.model.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message (
    val id: String,
    val uid: String,
    val text: String = "",
    val mentions: List<Mention> = emptyList(),
    val seen: List<String> = emptyList(),
    val replyMessageId: String? = null,
    val replyMessage : Message? = null,
    val attachments : List<Attachment> = emptyList(),
    val createdAt: Long,
    val updatedAt: Long,
) : Parcelable

@Parcelize
data class Mention(
    val id: String,
    val content: String,
) : Parcelable

@Parcelize
data class Attachment(
    val id: String,
    val uid: String,
    val url: String,
    val thumbnail: String,
    val inVideoType: Boolean,
    val createdTime: Long
) : Parcelable

val mockingMessage = Message(
    id = "mockingMessage",
    uid = "uid",
    text = "mocking message is here",
    createdAt = 0,
    updatedAt = 0,
)