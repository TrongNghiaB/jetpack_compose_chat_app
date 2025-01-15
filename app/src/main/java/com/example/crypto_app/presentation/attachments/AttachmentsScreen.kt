package com.example.crypto_app.presentation.attachments

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.SubcomposeAsyncImage
import com.example.crypto_app.R
import com.example.crypto_app.domain.manager.CommonUtil.Companion.noRippleClickable
import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.util.Constants
import com.example.crypto_app.util.Constants.IconSize40
import com.example.crypto_app.util.Constants.Padding10
import com.example.crypto_app.util.Constants.Padding20
import com.example.crypto_app.util.Constants.Padding5
import com.example.crypto_app.util.Constants.Padding50

@Composable
fun AttachmentsScreen(
    modifier: Modifier = Modifier,
    attachmentViewModel: AttachmentsViewModel,
    index : Int,
    onBackClick: () -> Unit,) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = index,pageCount = { attachmentViewModel.attachments.size })

    LaunchedEffect(pagerState.currentPage) {
        attachmentViewModel.changePage()
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = Constants.Padding24, horizontal = Padding10)
            .statusBarsPadding()
    ) { page ->
        val attachment = attachmentViewModel.attachments[page]
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            TopButtonsActionAttachment(onBackClick = onBackClick)
            if (attachment.inVideoType) {
                val player = remember {
                    attachmentViewModel.getNewVideoPlayer(context,page,attachment.url)
                }
                AttachmentVideoView(attachment = attachment, player = player )
            } else {
                AttachmentImageView(attachment = attachment)
            }
        }
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun AttachmentVideoView(modifier: Modifier = Modifier, attachment: Attachment,player: ExoPlayer) {
    AndroidView(
        factory = {context ->
            PlayerView(context).apply {
                this.player = player
            }
        },
        modifier = modifier
            .padding(vertical = Padding20)
            .fillMaxSize()
    )
}

@Composable
fun AttachmentImageView(modifier: Modifier = Modifier, attachment: Attachment) {
    SubcomposeAsyncImage(
        model = attachment.url,
        contentDescription = "media message",
        contentScale = ContentScale.Fit,
        error = {
            Image(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        },
        loading = {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(IconSize40)
                        .align(Alignment.Center)
                )
            }
        },
        modifier = modifier
            .padding(vertical = Padding20)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(Padding10)
            )
            .fillMaxSize()
    )
}

@Composable
fun TopButtonsActionAttachment(modifier: Modifier = Modifier,onBackClick: () -> Unit) {
    Row() {
        Icon(
            painter = painterResource(id = R.drawable.ic_cancel),
            contentDescription = "back button",
            modifier = modifier.noRippleClickable {
                onBackClick()
            }
        )
        Spacer(modifier = modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_download),
            contentDescription = "back button",
            modifier = modifier.noRippleClickable {

            }
        )
        Spacer(modifier = modifier.width(Padding5))
        Icon(
            painter = painterResource(id = R.drawable.ic_more_vert),
            contentDescription = "back button",
            modifier = modifier.noRippleClickable {

            }
        )
    }
}

@Preview()
@Composable
private fun AttachmentsPrev() {
    val modifier = Modifier
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = modifier
                    .size(Padding50)
                    .background(Color.LightGray.copy(alpha = 0.25f))
            )
        }
    }
}