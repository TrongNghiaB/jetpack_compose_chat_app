package com.example.crypto_app.domain.manager

import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.chat.Mention
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.model.profile.User
import com.google.firebase.firestore.DocumentSnapshot
import java.util.concurrent.TimeUnit

fun Map<String, Any>.toChatUser(): User {
    return User(
        id = this["id"] as String,
        email = this["email"] as String,
        name = this["name"] as String,
        image = this["image"] as String?,
    )
}

fun Map<String, Any>.toChatChannel(): ChatChannel {
    return ChatChannel(
        id = this["id"] as String,
        name = this["name"] as String,
        memberIds = (this["memberIds"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
        createdAt = this["createdAt"] as Long,
        updatedAt = this["updatedAt"] as Long,
        latestMessage = this["latestMessage"] as String,
    )
}

fun Map<String, Any>.toAttachment(): Attachment {
    return Attachment(
        id = this["id"] as? String ?: "",
        uid = this["uid"] as? String ?: "",
        url = this["url"] as? String ?: "",
        thumbnail = this["thumbnail"] as? String ?: "",
        inVideoType = this["inVideoType"] as? Boolean ?: false,
        createdTime = this["createdTime"] as? Long ?: 0
    )
}

fun DocumentSnapshot.toChatChannel(): ChatChannel {
    return ChatChannel(
        id = this["id"] as String,
        name = this["name"] as String,
        memberIds = (this["memberIds"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
        createdAt = this["createdAt"] as? Long ?: 0,
        updatedAt = this["updatedAt"] as? Long ?: 0,
        latestMessage = this["latestMessage"] as String,
    )
}

fun DocumentSnapshot.toMessage(): Message {

    return Message(
        id = this["id"] as String,
        uid = this["uid"] as String,
        text = (this["text"] as String).trim(),
        mentions = (this["memberIds"] as? List<*>)?.filterIsInstance<Mention>()
            ?: emptyList(),
        attachments = (this["attachments"] as? List<Map<String, Any>>)?.map { it.toAttachment() }
            ?: emptyList(),
        seen = (this["seen"] as? List<*>)?.filterIsInstance<String>()
            ?: emptyList(),
        replyMessageId = this["replyMessageId"] as String?,
        replyMessage = this["replyMessage"] as Message?,
        createdAt = this["createdAt"] as Long,
        updatedAt = this["updatedAt"] as Long,
    )
}


fun Long.timeAgo(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "just now"
        diff < TimeUnit.MINUTES.toMillis(60) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} minutes ago"
        diff < TimeUnit.HOURS.toMillis(24) -> "${TimeUnit.MILLISECONDS.toHours(diff)} hours ago"
        diff < TimeUnit.DAYS.toMillis(7) -> "${TimeUnit.MILLISECONDS.toDays(diff)} days ago"
        else -> {
            val weeks = TimeUnit.MILLISECONDS.toDays(diff) / 7
            "$weeks weeks ago"
        }
    }
}


