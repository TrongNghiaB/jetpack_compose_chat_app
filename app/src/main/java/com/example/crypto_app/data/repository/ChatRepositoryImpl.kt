package com.example.crypto_app.data.repository

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.crypto_app.domain.manager.CommonUtil.Companion.isImageFile
import com.example.crypto_app.domain.manager.FirebaseStorageHelper.Companion.uploadMediaToFirebase
import com.example.crypto_app.domain.manager.FirebaseStorageHelper.Companion.uploadVideoThumbnailToFirebaseStorage
import com.example.crypto_app.domain.manager.toChatChannel
import com.example.crypto_app.domain.manager.toChatUser
import com.example.crypto_app.domain.manager.toMessage
import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.domain.model.chat.ChatChannel
import com.example.crypto_app.domain.model.chat.Mention
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.repository.ChatRepository
import com.example.crypto_app.util.DateUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepositoryImpl : ChatRepository {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val firestoreInstance = FirebaseFirestore.getInstance()
    private val userCollection = firestoreInstance.collection("users")
    private val chatChannelCollection = firestoreInstance.collection("ChatChannels")
    override suspend fun searchUser(input: String): List<User> {
        if (input.trim().isEmpty()) return emptyList()
        try {
            val result = userCollection
                .whereGreaterThanOrEqualTo("name", input.trim().lowercase())
                .whereLessThanOrEqualTo("name", "${input.trim().lowercase()}\uF7FF")
                .get()
                .await()
            val listUsers = result.documents.mapNotNull {
                it.data?.toChatUser()
            }
            return listUsers
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun getAllUsers(): List<User> {
        try {
            val result = userCollection
                .get()
                .await()
            val listUsers = result.documents.mapNotNull {
                it.data?.toChatUser()
            }
            return listUsers
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun addNewChatChannel(chatChannel: ChatChannel): Result<Unit> {
        try {
            chatChannelCollection
                .document(chatChannel.id)
                .set(chatChannel)
                .await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun updateChatChannel(
        chatChannel: ChatChannel,
        updateValue: Map<String, Any>
    ): Result<Unit> {
        try {
            chatChannelCollection
                .document(chatChannel.id)
                .update(updateValue)
                .await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateChatChannelName(
        chatChannel: ChatChannel,
        name: String,
        now: Long?,
    ): Result<Unit> {
        val updatedTime = now ?: DateUtil.getLocalDateTimeNowToMilliseconds()
        return updateChatChannel(
            chatChannel,
            mapOf(
                "name" to name,
                "updatedAt" to updatedTime,
                "latestMessage" to "Channel name have been change to $name"
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateChannelLastMessage(
        chatChannel: ChatChannel,
        latestMessage: String,
        now: Long?,
    ): Result<Unit> {
        val updatedTime = now ?: DateUtil.getLocalDateTimeNowToMilliseconds()
        return updateChatChannel(
            chatChannel,
            mapOf("latestMessage" to latestMessage, "updatedAt" to updatedTime)
        )
    }


    override fun getChatChannels(): Flow<QuerySnapshot> = callbackFlow {
        val listener = chatChannelCollection.whereArrayContains("memberIds", currentUser?.uid ?: "")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // close the flow in case of an error
                    return@addSnapshotListener
                }
                snapshot?.let { trySend(it).isSuccess }
            }

        awaitClose { listener.remove() } // Stop listening when flow collection is stopped
    }

    override suspend fun getChatChannelsDetail(chatChannelId: String): ChatChannel? {
        try {
            val result = chatChannelCollection
                .document(chatChannelId)
                .get()
                .await()
            val chatChannel = result.toChatChannel()
            return chatChannel
        } catch (e: Exception) {
            return null
        }
    }

    override fun observeChatChannelMessage(
        chatChannel: ChatChannel,
        onMessageAdded: (Message) -> Unit,
        onMessageUpdated: (Message) -> Unit,
        onMessageRemoved: (Message) -> Unit
    ) {
        var lastDocumentSnapshot: DocumentSnapshot? = null
        //get the last message document
        chatChannelCollection
            .document(chatChannel.id)
            .collection("Messages")
            .limit(1)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    lastDocumentSnapshot = it.documents.first()
                    listenForNewMessages(chatChannel.id, lastDocumentSnapshot, onMessageAdded)
                } else {
                    listenForNewMessages(chatChannel.id, lastDocumentSnapshot, onMessageAdded)
                }
            }
    }

    private fun listenForNewMessages(
        chatChannelId: String,
        lastDocumentSnapshot: DocumentSnapshot? = null,
        onMessageAdded: (Message) -> Unit,
    ) {
        val query = if (lastDocumentSnapshot != null)
            chatChannelCollection
                .document(chatChannelId)
                .collection("Messages")
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .startAfter(lastDocumentSnapshot)
        else chatChannelCollection
            .document(chatChannelId)
            .collection("Messages")
            .orderBy("createdAt", Query.Direction.ASCENDING)

        query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error observeChatChannelMessage 1: ${error.message}")
                return@addSnapshotListener
            }
            snapshot?.let {
                for (change in snapshot.documentChanges) {
                    try {
                        if (change.type == DocumentChange.Type.ADDED) {
                            val message = change.document.toMessage()
                            onMessageAdded(message)
                        }
                    } catch (e: Exception) {
                        println("Error observeChatChannelMessage 2: ${e.message}")
                    }
                }
            }
        }
    }

    override suspend fun getChatChannelsDetailMessages(
        chatChannelId: String,
        lastVisibleMessage: DocumentSnapshot?
    ): Pair<List<Message>, DocumentSnapshot?> {
        var query = chatChannelCollection
            .document(chatChannelId)
            .collection("Messages")
            .limit(5)
            .orderBy("createdAt", Query.Direction.DESCENDING)

        // If we have the last visible document, start after it
        if (lastVisibleMessage != null) {
            query = query.startAfter(lastVisibleMessage)
        }

        return try {
            val snapshot = query.get().await()
            val messages = snapshot.documents.map { it.toMessage() }

            // Return the list of messages and the last visible document
            Pair(messages, if (snapshot.isEmpty) null else snapshot.documents.last())
        } catch (e: Exception) {
            Log.i("error ChatRepo getChatChannelsDetailMessages", "$e")
            Pair(emptyList(), null)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendMessage(
        context: Context,
        chatChannel: ChatChannel,
        text: String,
        mentions: List<Mention>,
        attachments: List<Uri>,
        replyMessage: Message?
    ): Result<Unit> {
        currentUser?.let {
            val now = DateUtil.getLocalDateTimeNowToMilliseconds()
            val listAttachment = mutableListOf<Attachment>()
            uploadMediaToFirebase(context, attachments).forEachIndexed() { index, url ->
                val isVideo = !isImageFile(attachments[index], context)
                val thumbnail = if (isVideo) uploadVideoThumbnailToFirebaseStorage(
                    context,
                    attachments[index]
                ) else url

                val newAttachment = Attachment(
                    id = UUID.randomUUID().toString(),
                    uid = it.uid,
                    url = url,
                    thumbnail = thumbnail,
                    inVideoType = isVideo,
                    createdTime = now
                )
                listAttachment.add(newAttachment)
            }

            val message = Message(
                id = UUID.randomUUID().toString(),
                uid = currentUser.uid,
                text = text,
                attachments = listAttachment,
                mentions = mentions,
                replyMessage = replyMessage,
                replyMessageId = replyMessage?.id,
                createdAt = now,
                updatedAt = now,
            )
            return try {
                chatChannelCollection
                    .document(chatChannel.id)
                    .collection("Messages")
                    .document(message.id)
                    .set(message)
                    .await()

                updateChannelLastMessage(chatChannel, text)

            } catch (e: Exception) {
                println("Error send message: ${e.message}")
                Result.failure(e)
            }
        }
        Log.i("Error send message", "currentUser is null")
        return Result.failure(Exception())
    }
}
