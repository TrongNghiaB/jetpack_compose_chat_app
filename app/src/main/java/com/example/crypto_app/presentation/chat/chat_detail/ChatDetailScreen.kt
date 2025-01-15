package com.example.crypto_app.presentation.chat.chat_detail

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.crypto_app.R
import com.example.crypto_app.domain.manager.CommonUtil.Companion.getMaxMessageSize
import com.example.crypto_app.domain.manager.CommonUtil.Companion.getVideoThumbnail
import com.example.crypto_app.domain.manager.CommonUtil.Companion.isImageFile
import com.example.crypto_app.domain.manager.CommonUtil.Companion.noRippleClickable
import com.example.crypto_app.domain.manager.CommonUtil.Companion.reachedBottom
import com.example.crypto_app.domain.manager.timeAgo
import com.example.crypto_app.domain.model.chat.Attachment
import com.example.crypto_app.domain.model.chat.Message
import com.example.crypto_app.presentation.chat.ChatViewModel
import com.example.crypto_app.presentation.commons.CommonAppBar
import com.example.crypto_app.presentation.commons.CommonLargeSpaceBox
import com.example.crypto_app.presentation.commons.CommonRoundIconButton
import com.example.crypto_app.presentation.commons.ImagePicker
import com.example.crypto_app.ui.theme.MessengerButtonColor
import com.example.crypto_app.util.Constants.IconSize30
import com.example.crypto_app.util.Constants.IconSize40
import com.example.crypto_app.util.Constants.Padding0
import com.example.crypto_app.util.Constants.Padding10
import com.example.crypto_app.util.Constants.Padding15
import com.example.crypto_app.util.Constants.Padding20
import com.example.crypto_app.util.Constants.Padding30
import com.example.crypto_app.util.Constants.Padding35
import com.example.crypto_app.util.Constants.Padding5
import com.example.crypto_app.util.Constants.Padding50
import com.example.crypto_app.util.Constants.Padding70


@Composable
fun ChatDetailScreen(
    modifier: Modifier = Modifier,
    chatChannelId: String,
    chatViewModel: ChatViewModel,
    chatDetailViewModel: ChatDetailViewModel,
    navigateToAttachmentsScreen: (List<Attachment>, Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val reachedBottom: Boolean by remember {
        derivedStateOf { listState.reachedBottom() }
    }

    BackHandler {
        onBackClick()
    }

    LaunchedEffect(Unit) {
        chatDetailViewModel.getChatChannelDetail(chatChannelId)
    }

    LaunchedEffect(reachedBottom) {
        if (!chatDetailViewModel.canLoadMoreMessage) return@LaunchedEffect
        if (reachedBottom && !chatDetailViewModel.isLoadingMore)
            chatDetailViewModel.fetchChannelMessages(chatChannelId)
    }

    if (chatDetailViewModel.isLoading) {
        CommonLargeSpaceBox(content = { CircularProgressIndicator() })
    } else {
        if (chatDetailViewModel.chatChannel == null) {
            CommonLargeSpaceBox(content = { Text("Can not load chat channel information") })
        } else {
            Scaffold(
                bottomBar = {
                    ChatBar(
                        chatViewModel = chatViewModel,
                        chatDetailViewModel = chatDetailViewModel,
                        chatChannelId = chatChannelId
                    )
                },
                topBar = {
                    DetailChatAppBar(onBackClick = {
                        onBackClick()
                    })
                },
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = Padding5)
                    .noRippleClickable {
                        focusManager.clearFocus()
                    }
            ) {
                val bottomPadding = it.calculateBottomPadding()
                val topPadding = it.calculateTopPadding()
                LazyColumn(
                    state = listState,
                    reverseLayout = true,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = topPadding, bottom = bottomPadding)
                ) {
                    itemsIndexed(
                        items = chatDetailViewModel.messages,
                    ) { _, message ->
                        MessageBubble(
                            modifier = modifier,
                            chatViewModel = chatViewModel,
                            message = message,
                            chatDetailViewModel = chatDetailViewModel,
                            navigateToAttachmentsScreen = navigateToAttachmentsScreen,
                        )
                    }

                    if (chatDetailViewModel.isLoadingMore) {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = modifier.fillMaxWidth()
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ChatBar(modifier: Modifier = Modifier,
            chatViewModel: ChatViewModel,
            chatDetailViewModel: ChatDetailViewModel,
            chatChannelId: String) {
    val buttonPadding = modifier.padding(horizontal = Padding5)
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Column {
            LazyRow {
                items(chatDetailViewModel.listImageUrl) { uri ->
                    uri?.let {
                        val isImage = isImageFile(uri, context)
                        val thumb = if (isImage) uri
                        else getVideoThumbnail(context, uri) ?: R.drawable.ic_rectangle_play

                        Box {
                            Image(
                                painter = rememberAsyncImagePainter(model = thumb),
                                contentDescription = "Cropped Image",
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .padding(end = Padding5)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(Padding10)
                                    )
                                    .size(Padding70)
                            )
                            if (!isImage)
                                Box(
                                    modifier = Modifier
                                        .size(Padding70)
                                        .background(color = Color.LightGray.copy(alpha = 0.5f))
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_rectangle_play),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(IconSize30)
                                            .align(Alignment.Center),
                                        tint = Color.Black
                                    )
                                }
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding20, horizontal = Padding5)
            ) {
                CommonRoundIconButton(
                    backgroundColor = MessengerButtonColor,
                    modifier = buttonPadding.noRippleClickable {},
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = null,
                        modifier = Modifier
                            .size(IconSize30)
                            .padding(Padding5),
                        tint = Color.Black
                    )
                }
                CommonRoundIconButton(
                    modifier = buttonPadding,
                    backgroundColor = MessengerButtonColor
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier
                            .size(IconSize30)
                            .padding(Padding5),
                        tint = Color.Black
                    )
                }
                ImagePicker(
                    allowMultiple = true,
                    enabled = chatDetailViewModel.listImageUrl.size < 5,
                    onMediaPicked = {
                        chatDetailViewModel.onImagePicked(it)
                    },
                    modifier = modifier
                        .clip(CircleShape)
                ) {
                    CommonRoundIconButton(
                        modifier = buttonPadding,
                        backgroundColor = MessengerButtonColor
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(IconSize30)
                                .padding(Padding5),
                            tint = Color.Black
                        )
                    }
                }
                TextField(
                    value = chatDetailViewModel.chatTextField,
                    onValueChange = { chatDetailViewModel.onChatTextFieldValueChange(it) },
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.LightGray.copy(alpha = 0.4f),
                        focusedContainerColor = Color.LightGray.copy(alpha = 0.4f),
                        errorContainerColor = Color.LightGray.copy(alpha = 0.4f),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    modifier = modifier
                        .height(Padding50)
                        .weight(1f)
                        .background(shape = CircleShape, color = Color.LightGray)
                )

                CommonRoundIconButton(
                    modifier = buttonPadding
                        .noRippleClickable(enabled = !chatDetailViewModel.isSendingMessage) {
                            chatDetailViewModel.sendMessage(context = context)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null,
                        modifier = Modifier
                            .size(IconSize30)
                            .padding(Padding5),
                        tint = if (chatDetailViewModel.isSendingMessage) Color.Black else MessengerButtonColor
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    modifier: Modifier = Modifier,
    message: Message,
    chatDetailViewModel: ChatDetailViewModel,
    chatViewModel: ChatViewModel,
    navigateToAttachmentsScreen: (List<Attachment>, Int) -> Unit,
) {
    val showMessageSeenAndTime = chatDetailViewModel.showMessageSeenAndTime
    val isMine = message.uid == chatDetailViewModel.currentUser?.uid
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Padding5),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start,
    ) {
        if (showMessageSeenAndTime == message.id)
            Text(
                message.updatedAt.timeAgo(),
                color = Color.Black,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = Padding5)
            )

        Box(
            modifier = modifier
                .widthIn(max = (LocalConfiguration.current.screenWidthDp.dp * (2 / 3f)))
                .noRippleClickable(
                    onClick = {
                        chatDetailViewModel.onShowMessageSeenAndTime(message.id)
                    },
                )
                .padding(vertical = Padding10)
                .background(
                    color = if (isMine) MessengerButtonColor else Color.LightGray.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(
                        topStart = Padding30,
                        topEnd = Padding30,
                        bottomEnd = if (isMine) Padding0 else Padding30,
                        bottomStart = if (isMine) Padding30 else Padding0,
                    )
                )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding10, horizontal = Padding15),
                horizontalAlignment = Alignment.Start
            ) {
                MediaAttachmentChatItem(
                    message = message,
                    navigateToAttachmentsScreen = navigateToAttachmentsScreen
                )

                Text(text = message.text)
            }

        }

        if (isMine) {
            if (showMessageSeenAndTime == message.id && message.seen.isNotEmpty()) {
                Text(
                    text = "seen",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MediaAttachmentChatItem(
    modifier: Modifier = Modifier,
    message: Message,
    navigateToAttachmentsScreen: (List<Attachment>, Int) -> Unit
) {
    val itemSize = (getMaxMessageSize() - Padding35) / 2
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(Padding5),
        horizontalArrangement = Arrangement.spacedBy(Padding5)
    ) {
        message.attachments.forEachIndexed { index, attachment ->
            Box(modifier = modifier.noRippleClickable {
                navigateToAttachmentsScreen(message.attachments, index)
            }) {
                SubcomposeAsyncImage(
                    model = attachment.thumbnail,
                    contentDescription = "media message",
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_image),
                            contentDescription = null
                        )
                    },
                    loading = {
                        CircularProgressIndicator(modifier = modifier.padding(Padding20))
                    },
                    modifier = Modifier
                        .size(itemSize)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(Padding10)
                        )
                )
                if (attachment.inVideoType)
                    Box(
                        modifier = Modifier
                            .size(itemSize)
                            .background(color = Color.LightGray.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_rectangle_play),
                            contentDescription = null,
                            modifier = Modifier
                                .size(IconSize30)
                                .align(Alignment.Center),
                            tint = Color.Black
                        )
                    }
            }
        }
    }
}

@Composable
fun DetailChatAppBar(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    CommonAppBar(
        containerColor = Color.White,
        onBackClick = onBackClick,
        title = {
            Text(
                "Channel Name",
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold
            )
        },
        actions = {
            Row {
                CommonRoundIconButton(backgroundColor = Color.Transparent) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = null,
                        modifier = Modifier
                            .size(IconSize40)
                            .padding(Padding5),
                        tint = MessengerButtonColor
                    )
                }
                Spacer(modifier = modifier.width(Padding10))
                CommonRoundIconButton(backgroundColor = Color.Transparent) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_call_video),
                        contentDescription = null,
                        modifier = Modifier
                            .size(IconSize40)
                            .padding(Padding5),
                        tint = MessengerButtonColor
                    )
                }
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PrevChatDetailScreen() {
    Surface {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(R.drawable.ic_image),
                contentDescription = "Cropped Image",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Blue),
                modifier = Modifier
                    .padding(end = Padding5)
                    .size(Padding70)
            )

        }
    }
}

//@Composable
//fun OtherMessage(modifier: Modifier = Modifier,
//                 message: Message,
//                 chatDetailViewModel: ChatDetailViewModel,
//                 chatViewModel: ChatViewModel) {
//    val showMessageSeenAndTime = chatDetailViewModel.showMessageSeenAndTime
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(bottom = Padding5),
//        horizontalAlignment = Alignment.Start,
//    ) {
//        if (showMessageSeenAndTime == message.id)
//            Text(
//                message.updatedAt.timeAgo(),
//                color = Color.Black,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(bottom = Padding5)
//            )
//        Row(verticalAlignment = Alignment.Bottom) {
//            AsyncImage(
//                model = "",
//                contentDescription = "user avatar",
//                error = painterResource(id = R.drawable.ic_person),
//                placeholder = painterResource(id = R.drawable.ic_person),
//                modifier = modifier
//                    .padding(end = Padding5)
//                    .size(IconSize30)
//                    .background(color = Color.Red, shape = CircleShape)
//            )
//            Box(
//                modifier = Modifier
//                    .widthIn(max = (LocalConfiguration.current.screenWidthDp.dp * (2 / 3f)))
//                    .noRippleClickable(
//                        onClick = {
//                            chatDetailViewModel.onShowMessageSeenAndTime(message.id)
//                        },
//                    )
//                    .background(
//                        color = Color.LightGray.copy(alpha = 0.6f),
//                        shape = RoundedCornerShape(
//                            topStart = Padding30,
//                            topEnd = Padding30,
//                            bottomEnd = Padding30
//                        )
//                    )
//            ) {
//                Text(
//                    text = message.text,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .padding(vertical = Padding10, horizontal = Padding15),
//                )
//
//            }
//        }
//    }
//}