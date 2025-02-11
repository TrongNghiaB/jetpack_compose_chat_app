package com.example.crypto_app.presentation.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.crypto_app.R
import com.example.crypto_app.presentation.authentication.AuthViewModel
import com.example.crypto_app.presentation.commons.CommonLargeSpaceBox
import com.example.crypto_app.presentation.search.components.SearchScreenAppBar
import com.example.crypto_app.ui.theme.LabelColor
import com.example.crypto_app.ui.theme.LightLabelColor
import com.example.crypto_app.ui.theme.MainLogoColor
import com.example.crypto_app.util.Constants
import com.example.crypto_app.util.Constants.IconSize25
import com.example.crypto_app.util.Constants.IconSize40
import com.example.crypto_app.util.Constants.Padding10
import com.example.crypto_app.util.Constants.Padding20

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    authViewModel: AuthViewModel,
    onBackClick: () -> Unit,
    navigateToDetails: (String) -> Unit
) {
    val state = searchViewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = Constants.Padding24, horizontal = Padding10)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {

        SearchScreenAppBar(viewModel = searchViewModel, onBackClick = onBackClick)
        Spacer(modifier = modifier.height(Padding20))


        if (searchViewModel.isLoading) {
            CommonLargeSpaceBox(content = { CircularProgressIndicator() })
        } else {
            when {
                state.userList!!.isEmpty() -> {
                    CommonLargeSpaceBox(content = {
                        Text(text = "No one match with input", color = LabelColor)
                    })
                }

                else -> {
                    if (state.addedToUsersList.isNotEmpty()) {
                        ListAddedUserToChat(viewModel = searchViewModel)
                        Spacer(modifier = modifier.height(Padding10))
                        TextButton(
                            modifier = modifier.align(Alignment.End),
                            onClick = {
                                searchViewModel.addNewChatChannel()
                            }) {
                            Text(text = "Create Channel", color = MainLogoColor)
                        }
                    }
                    SearchUserList(viewModel = searchViewModel, authViewModel = authViewModel)
//                        SearchCoinList(viewModel = viewModel, navigateToDetails = navigateToDetails)
                }
            }
        }
    }
}


@Composable
fun SearchUserList(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    viewModel: SearchViewModel,
//    navigateToDetails: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = Padding10)
    ) {
        viewModel.state.userList!!.forEach { user ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding10)
                    .clickable {
//                        navigateToDetails(coin.id)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = user.image,
                    contentDescription = "",
                    error = painterResource(id = R.drawable.ic_error),
                    placeholder = painterResource(id = R.drawable.ic_person),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(IconSize40)
                        .clip(CircleShape),
                )
                Spacer(modifier = modifier.width(Padding10))
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(text = user.name, color = LightLabelColor)
                    Text(text = user.email, color = Color.Gray)
                }
                Spacer(modifier = modifier.weight(1f))
                if(authViewModel.currentUser?.uid != user.id )
                RadioButton(
                    selected = viewModel.isUserAddedToChat(user),
                    onClick = {
                        viewModel.updateAddedChatUsersList(user)
                    }
                )
            }
        }
    }
}


@Composable
fun ListAddedUserToChat(modifier: Modifier = Modifier, viewModel: SearchViewModel) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding10)
            .horizontalScroll(rememberScrollState()),
    ) {
        viewModel.state.addedToUsersList.forEach { user ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = user.image,
                        contentDescription = "",
                        error = painterResource(id = R.drawable.ic_error),
                        placeholder = painterResource(id = R.drawable.ic_person),
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(IconSize40)
                            .clip(CircleShape)
                    )

                    IconButton(
                        modifier = Modifier
                            .size(IconSize25)
                            .offset(Constants.Padding12, -Padding10),
                        onClick = { viewModel.updateAddedChatUsersList(user) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel),
                            contentDescription = null,
                            tint = LightLabelColor,
                        )
                    }
                }
                Text(
                    text = user.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .width(IconSize40)
                )
            }
        }
    }
}


//@Preview
//@Composable
//private fun PrevAddChat() {
//    ListAddedUserToChat()
//}