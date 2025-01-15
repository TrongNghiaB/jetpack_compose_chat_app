package com.example.crypto_app.presentation.attachments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.util.Constants.PIKACHU_LINK_IMAGE
import javax.inject.Inject


class AttachmentsViewModel @Inject constructor() : ViewModel(){
    val sampleAttachments = listOf(
        Attachment(
            id = "1",
            uid = "uid",
            url = "https://firebasestorage.googleapis.com/v0/b/crypto-app-b8e59.appspot.com/o/media%2Fimages%2F1000005234?alt=media&token=b90621de-dbda-41fa-8c92-56a73af9e537",
            thumbnail = PIKACHU_LINK_IMAGE,
            inVideoType = false,
            createdTime = 0
        ),
        Attachment(
            id = "2",
            uid = "uid2",
            url = "https://firebasestorage.googleapis.com/v0/b/crypto-app-b8e59.appspot.com/o/media%2Fimages%2F1000005205?alt=media&token=b1a41ed0-96bf-493d-a051-ffae217ce39a",
            thumbnail = PIKACHU_LINK_IMAGE,
            inVideoType = false,
            createdTime = 0
        ),
        Attachment(
            id = "3",
            uid = "uid3",
            url = "https://firebasestorage.googleapis.com/v0/b/crypto-app-b8e59.appspot.com/o/media%2Fvideos%2F1000005122?alt=media&token=caa5bda8-457c-460c-a0b4-6fd1f57b8101",
            thumbnail = PIKACHU_LINK_IMAGE,
            inVideoType = true,
            createdTime = 0
        ),
        Attachment(
            id = "4",
            uid = "uid",
            url = "https://firebasestorage.googleapis.com/v0/b/crypto-app-b8e59.appspot.com/o/media%2Fvideos%2F1000005155?alt=media&token=42fe6b34-b697-48e1-972e-67097fccfb83",
            thumbnail = PIKACHU_LINK_IMAGE,
            inVideoType = true,
            createdTime = 0
        )
    )
    var attachments by mutableStateOf( emptyList<Attachment>())
    private val playerMap = mutableStateMapOf<Int, ExoPlayer>()

    fun setAttachmentsList(attachmentsList: List<Attachment>) {
        attachments = attachmentsList
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun getNewVideoPlayer(context: Context,page: Int,url:String): ExoPlayer {
        return playerMap.getOrPut(page) {
            ExoPlayer.Builder(context)
                .setSeekBackIncrementMs(10000L)
                .setSeekForwardIncrementMs(10000L)
                .build()
                .apply {
                    setMediaItem(MediaItem.fromUri(Uri.parse(url)))
                    prepare()
                    playWhenReady = false
                    repeatMode = Player.REPEAT_MODE_OFF
                }
        }
    }

    fun changePage() {
        playerMap.forEach { (_, player) ->
            player.playWhenReady = false
        }
    }

    private fun releaseAllPlayers() {
        playerMap.values.forEach { it.release() }
        playerMap.clear()
    }

    override fun onCleared() {
        releaseAllPlayers()
        super.onCleared()
    }
}
