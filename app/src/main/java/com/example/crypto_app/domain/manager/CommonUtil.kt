package com.example.crypto_app.domain.manager

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.size.Size
import java.io.ByteArrayOutputStream
import java.util.Locale

class CommonUtil {
    companion object {
        private var toast by mutableStateOf<Toast?>(null)
        fun showToast(context: Context, text: String) {
            toast?.cancel()
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast?.show()
        }

        @Composable
        fun getMaxMessageSize(): Dp = LocalConfiguration.current.screenWidthDp.dp * (2 / 3f)

        @Composable
        fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier =
            this.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
                enabled = enabled,
            )

        fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
            val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
            return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
        }

        fun LazyListState.reachedTop(): Boolean {
            val firstVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
            return firstVisibleItem?.index == -1 && firstVisibleItem.offset == -1
        }

        fun isImageFile(uri: Uri, context: Context): Boolean {
            getMimeType(uri, context)?.let {
                if (it.contains("image")) return true
            }
            return false
        }

        fun getMimeType(uri: Uri,context: Context): String? {
            var mimeType: String? = null
            if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
                val cr = context.contentResolver
                mimeType = cr.getType(uri)
            } else {
                val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                    uri
                        .toString()
                )
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.lowercase(Locale.getDefault())
                )
            }
            return mimeType
        }

        fun getVideoThumbnail(context: Context, uri: Uri): Bitmap? {
            val retriever = MediaMetadataRetriever()
            try {
                // Set the data source to the video URI
                retriever.setDataSource(context, uri)

                // Extract a frame at the 1st second (1000000 microseconds)
                return retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            } finally {
                retriever.release()  // Don't forget to release the retriever
            }
        }

        fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            return stream.toByteArray()
        }
    }
}
