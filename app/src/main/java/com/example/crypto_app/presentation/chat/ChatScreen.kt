package com.example.crypto_app.presentation.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.crypto_app.R
import com.example.crypto_app.domain.manager.timeAgo
import com.example.crypto_app.domain.manager.toChatChannel
import com.example.crypto_app.presentation.commons.CommonLargeSpaceBox
import com.example.crypto_app.presentation.commons.CommonRoundIconButton
import com.example.crypto_app.presentation.commons.PullToRefreshLazyColumn
import com.example.crypto_app.presentation.commons.SearchBar
import com.example.crypto_app.ui.theme.LabelColor
import com.example.crypto_app.util.Constants.FontSize10
import com.example.crypto_app.util.Constants.FontSize15
import com.example.crypto_app.util.Constants.FontSize20
import com.example.crypto_app.util.Constants.IconSize15
import com.example.crypto_app.util.Constants.IconSize30
import com.example.crypto_app.util.Constants.IconSize40
import com.example.crypto_app.util.Constants.Padding10
import com.example.crypto_app.util.Constants.Padding12
import com.example.crypto_app.util.Constants.Padding15
import com.example.crypto_app.util.Constants.Padding20
import com.example.crypto_app.util.Constants.Padding3
import com.example.crypto_app.util.Constants.Padding5
import com.example.crypto_app.util.Constants.Padding50
import com.example.crypto_app.util.DateUtil

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel,
    navigateToSearch: () -> Unit,
    navigateToDetailChat: (String) -> Unit,
) {
    PullToRefreshLazyColumn(
        isRefreshing = false,
        onRefresh = { },
        content = {
            ChatTopBar()

            Spacer(modifier = modifier.height(Padding20))

            SearchBar(
                text = "",
                readOnly = true,
                onValueChange = {},
                onClick = navigateToSearch,
                onSearch = {},
            )

            Spacer(modifier = modifier.height(Padding10))

            RowScrollAbleUsersList()

            Spacer(modifier = modifier.height(Padding20))

            TabChat(chatViewModel = chatViewModel)

            Spacer(modifier = modifier.height(Padding20))

            when (chatViewModel.selectedTabIndex) {
                0 -> TabChatContent(
                    chatViewModel = chatViewModel,
                    navigateToDetailChat = navigateToDetailChat
                )

                1 -> Text("Tab channels")
            }
        }
    )
}

@Composable
fun TabChatContent(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel,
    navigateToDetailChat: (String) -> Unit,
) {
    if (chatViewModel.chatChannels.isEmpty()) {
        CommonLargeSpaceBox {
            Text(text = "There is no chat channel", color = LabelColor)
        }
    } else {
        chatViewModel.chatChannels.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(vertical = Padding5)
                    .clickable {
                        navigateToDetailChat(it.id)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    modifier = modifier
                        .size(Padding50)
                        .background(color = Color.Red, shape = CircleShape)
                        .clip(CircleShape)
                )
                Spacer(modifier = modifier.width(Padding5))
                Column() {
                    Text(
                        it.name,
                        fontSize = FontSize15,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(Padding3))
                    Row {
                        Text(
                            it.latestMessage.ifEmpty { "Say hello to each other..." },
                            color = Color.Gray,
                            fontSize = FontSize15,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = modifier.weight(1f)
                        )
                        Text(it.updatedAt.timeAgo())
                    }
                }

            }
        }

    }
}

@Composable
fun TabChat(modifier: Modifier = Modifier, chatViewModel: ChatViewModel) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        chatViewModel.tabs.forEachIndexed { index, title ->
            Box(
                modifier = Modifier
                    .clickable {
                        chatViewModel.changeChatTab(index)
                    }
                    .background(
                        color = if (chatViewModel.selectedTabIndex == index) Color.LightGray.copy(
                            alpha = 0.5f
                        ) else Color.Transparent,
                        shape = CircleShape
                    )
                    .weight(1f)
                    .padding(horizontal = Padding10),

                contentAlignment = Alignment.Center,

                ) {
                Text(
                    text = title,
                    color = if (chatViewModel.selectedTabIndex == index) Color.Black else Color.Gray,
                    fontSize = FontSize15,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(Padding5)
                )
            }
        }
    }
}

@Composable
fun RowScrollAbleUsersList(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
    ) {
        for (a in 0..<10) {
            Box(modifier = modifier.padding(horizontal = Padding5)) {
                Column(verticalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null,
                        modifier = modifier
                            .size(Padding50)
                            .background(color = Color.Red, shape = CircleShape)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = modifier.height(Padding3))
                    Text("Bui T...", fontWeight = FontWeight.W500)
                }
            }
        }
    }
}

@Composable
fun ChatTopBar(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = modifier.size(IconSize40),
            contentAlignment = Alignment.Center
        ) {
            CommonRoundIconButton(modifier = modifier.align(Alignment.BottomCenter)) {
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
                modifier = modifier
                    .offset(Padding12, -Padding10),
                backgroundColor = Color.Red
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .size(IconSize15)
                        .padding(Padding3)
                ) {
                    Text(text = "5", color = Color.White, fontSize = FontSize10)
                }
            }
        }
        Spacer(modifier = modifier.width(Padding15))
        Text(
            "Chats",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = FontSize20
        )
        Spacer(modifier = modifier.weight(1f))
        CommonRoundIconButton {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null,
                modifier = Modifier
                    .size(IconSize30)
                    .padding(Padding5),
                tint = Color.Black
            )
        }
    }
}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
//@Composable
//private fun PreviewChatScreen() {
//    val chatViewModel: ChatViewModel = hiltViewModel()
//    ChatScreen(chatViewModel = chatViewModel)
//}