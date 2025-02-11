package com.example.crypto_app.presentation.profile

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import coil.compose.rememberAsyncImagePainter
import com.example.crypto_app.R
import com.example.crypto_app.presentation.authentication.AuthViewModel
import com.example.crypto_app.presentation.authentication.common_auth.CommonAuthTextField
import com.example.crypto_app.presentation.commons.ImagePicker
import com.example.crypto_app.ui.theme.ButtonColor
import com.example.crypto_app.ui.theme.DarkSecondary
import com.example.crypto_app.util.Constants.FontSize13
import com.example.crypto_app.util.Constants.FontSize15
import com.example.crypto_app.util.Constants.FontSize20
import com.example.crypto_app.util.Constants.FontSize30
import com.example.crypto_app.util.Constants.Padding10
import com.example.crypto_app.util.Constants.Padding100
import com.example.crypto_app.util.Constants.Padding15
import com.example.crypto_app.util.Constants.Padding20
import com.example.crypto_app.util.Constants.Padding24
import com.example.crypto_app.util.Constants.Padding30
import com.example.crypto_app.util.Constants.Padding5

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun ProfileScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = Padding24, horizontal = Padding10)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
    ) {
        if (profileViewModel.showEditProfilePopup) {
            EditProfilePopup(profileViewModel = profileViewModel)
        }

        Spacer(modifier = modifier.height(Padding10))
        ElevatedButton(onClick = { authViewModel.signOut() }) {
            Text("Log out")
        }
        AvatarHeader(profileViewModel = profileViewModel)
        Spacer(modifier = modifier.height(Padding10))
        NameAndEmail(profileViewModel = profileViewModel)

        HorizontalDivider(modifier = modifier.padding(vertical = Padding20))

        Text(text = "Total Balance", color = Color.LightGray, fontSize = FontSize15)
        Spacer(modifier = modifier.height(Padding5))

        HorizontalDivider(modifier = modifier.padding(vertical = Padding10), color = Color.Gray)


        Text(text = "Your Portfolio", color = Color.LightGray, fontSize = FontSize15)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditProfilePopup(modifier: Modifier = Modifier, profileViewModel: ProfileViewModel) {
    val user = profileViewModel.user
    val focusManager = LocalFocusManager.current
    focusManager.clearFocus()
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { profileViewModel.editProfilePopUpState(false) },
        offset = IntOffset(0, -200),
        properties = PopupProperties(focusable = true)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = Padding15)
                .background(Color.White.copy(alpha = 0.9f), shape = RoundedCornerShape(16.dp))
                .padding(Padding15),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "Edit Profile",
                    color = Color.Black,
                    fontSize = FontSize30,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Padding20))

                AvatarHeader(profileViewModel = profileViewModel, isEdit = true)
                Spacer(modifier = Modifier.height(Padding20))

                CommonAuthTextField(
                    title = "Email",
                    value = user?.email ?: "",
                    isDarkMode = true,
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(Padding10))

                CommonAuthTextField(
                    title = "UserName",
                    value = profileViewModel.userNameTextField,
                    isDarkMode = true,
                    onValueChange = { profileViewModel.onUserNameValueChange(it) },
                )
                Spacer(modifier = Modifier.height(Padding10))

                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        modifier = modifier.width(Padding100),
                        colors = ButtonDefaults.buttonColors(ButtonColor),
                        onClick = {
                            profileViewModel.editProfilePopUpState(false)
                        }) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.width(Padding10))
                    Button(
                        modifier = modifier.width(Padding100),
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        onClick = {
                            profileViewModel.editProfilePopUpState(false)
                        }) {
                        Text("Cancel", color = Color.White)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AvatarHeader(
    modifier: Modifier = Modifier, profileViewModel: ProfileViewModel, isEdit: Boolean = false
) {
    val selectedImageUri = profileViewModel.imageUri
    Box(
        modifier
            .fillMaxWidth()
            .height(Padding100),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = if (isEdit) {
                if (selectedImageUri == null) painterResource(id = R.drawable.ic_person)
                else rememberAsyncImagePainter(selectedImageUri)
            } else {
                if (profileViewModel.user?.image != null) {
                    val imageUri = Uri.parse(profileViewModel.user!!.image)
                    rememberAsyncImagePainter(imageUri)
                } else painterResource(id = R.drawable.ic_person)
            },
            contentDescription = null,
            colorFilter = if (selectedImageUri == null) {
                if (isEdit) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Black)
            } else null,
            modifier = modifier
                .size(Padding100)
                .background(color = if (isEdit) Color.Black else Color.White, shape = CircleShape)
                .clip(CircleShape)
        )
        if (isEdit) {
            ImagePicker(
                modifier = modifier
                    .offset(x = 40.dp, y = 40.dp)
                    .size(Padding30)
                    .clip(CircleShape),
                onMediaPicked = {
                    profileViewModel.onImagePicked(it.first())
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = null,
                    modifier = modifier
                        .size(Padding30)
                        .clip(CircleShape)
                        .background(color = Color.White, shape = CircleShape)
                )
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null,
                modifier = modifier
                    .offset(x = 40.dp, y = 40.dp)
                    .size(Padding30)
                    .clip(CircleShape)
                    .background(shape = CircleShape, color = DarkSecondary)
                    .padding(Padding5)
                    .background(shape = CircleShape, color = Color.White)
                    .clickable {
                        profileViewModel.editProfilePopUpState(true)
                    }
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NameAndEmail(modifier: Modifier = Modifier, profileViewModel: ProfileViewModel) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            if (profileViewModel.user?.name == null) "Can not get user name" else
                if (profileViewModel.user!!.name.isEmpty()) "username is empty" else profileViewModel.user!!.name,
            modifier = modifier.align(Alignment.CenterHorizontally),
            color = Color.White,
            fontSize = FontSize15
        )
        Text(
            profileViewModel.user?.email ?: "Can not get user email",
            modifier = modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            fontSize = FontSize13,
            style = TextStyle(fontStyle = FontStyle.Italic)
        )
    }
}
