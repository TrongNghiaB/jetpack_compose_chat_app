package com.example.crypto_app.presentation.profile

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.usecases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    var imageUri by mutableStateOf<Uri?>(null)
        private set
    var user by mutableStateOf<User?>(null)
        private set
    var showEditProfilePopup by mutableStateOf(false)
        private set
    var userNameTextField by mutableStateOf("")
        private set

    fun onImagePicked(uri: Uri?) {
        imageUri = uri
    }

    fun onUserNameValueChange(value: String) {
        userNameTextField = value
    }

    fun editProfilePopUpState(isShow: Boolean) {
        showEditProfilePopup = isShow
    }
}

