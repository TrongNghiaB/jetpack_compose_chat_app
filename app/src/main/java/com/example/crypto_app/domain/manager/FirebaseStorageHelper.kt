package com.example.crypto_app.domain.manager

import android.content.Context
import android.net.Uri
import com.example.crypto_app.domain.manager.CommonUtil.Companion.bitmapToByteArray
import com.example.crypto_app.domain.manager.CommonUtil.Companion.getVideoThumbnail
import com.example.crypto_app.domain.manager.CommonUtil.Companion.isImageFile
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseStorageHelper {
    companion object {
        private val firebaseStorageInstance = FirebaseStorage.getInstance()
        suspend fun uploadMediaToFirebase(
            context: Context,
            mediaUris: List<Uri>,
            folderPath: String = "media",
        ): List<String> {
            val listUrls = mutableListOf<String>()
            for (uri in mediaUris) {
                val isImage = isImageFile(uri, context)
                val path =
                    "$folderPath/${if (isImage) "images" else "videos"}/${uri.lastPathSegment}"
                val storageReference =
                    firebaseStorageInstance.reference.child(path)

                try {
                    storageReference.putFile(uri).await()
                    val downloadUrl = storageReference.downloadUrl.await()
                    listUrls.add(downloadUrl.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return listUrls
        }

        suspend fun uploadVideoThumbnailToFirebaseStorage(
            context: Context,
            videoUri: Uri
        ): String {
            val storageReference =
                firebaseStorageInstance.reference.child("thumbnails/${System.currentTimeMillis()}.jpg")

            // Get the thumbnail bitmap
            val thumbnail = getVideoThumbnail(context, videoUri)
            thumbnail?.let {
                val byteArray = bitmapToByteArray(it)
                try {
                    storageReference.putBytes(byteArray).await()
                    val downloadUrl = storageReference.downloadUrl.await()
                    return downloadUrl.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return ""
                }
            }

            return ""
        }
    }
}