package com.example.crypto_app.di
import com.example.crypto_app.data.local.SharePrefHelperImpl
import com.example.crypto_app.data.repository.AuthRepositoryImpl
import com.example.crypto_app.data.repository.ChatRepositoryImpl
import com.example.crypto_app.domain.manager.SharePrefHelper
import com.example.crypto_app.domain.repository.AuthRepository
import com.example.crypto_app.domain.repository.ChatRepository
import com.example.crypto_app.domain.usecases.auth.AuthUseCases
import com.example.crypto_app.domain.usecases.auth.ClearRememberMe
import com.example.crypto_app.domain.usecases.auth.GetRememberMe
import com.example.crypto_app.domain.usecases.auth.IsSignIn
import com.example.crypto_app.domain.usecases.auth.SaveRememberMe
import com.example.crypto_app.domain.usecases.auth.SignIn
import com.example.crypto_app.domain.usecases.auth.SignOut
import com.example.crypto_app.domain.usecases.auth.SignUp
import com.example.crypto_app.domain.usecases.chat.AddNewChatChannel
import com.example.crypto_app.domain.usecases.chat.ChatUseCases
import com.example.crypto_app.domain.usecases.chat.GetChatChannelDetail
import com.example.crypto_app.domain.usecases.chat.GetChatChannelDetailMessages
import com.example.crypto_app.domain.usecases.chat.GetChatChannels
import com.example.crypto_app.domain.usecases.chat.ObserveChatChannelMessage
import com.example.crypto_app.domain.usecases.chat.SearchUsers
import com.example.crypto_app.domain.usecases.chat.SendMessage
import com.example.crypto_app.domain.usecases.chat.UpdateChannelLastMessage
import com.example.crypto_app.domain.usecases.chat.UpdateChatChannelName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository = ChatRepositoryImpl()

    @Provides
    @Singleton
    fun provideChatUseCases(chatRepository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            searchUser = SearchUsers(chatRepository),
            addNewChatChannel = AddNewChatChannel(chatRepository),
            getChatChannels = GetChatChannels(chatRepository),
            sendMessage = SendMessage(chatRepository),
            updateChatChannelName = UpdateChatChannelName(chatRepository),
            updateChannelLastMessage = UpdateChannelLastMessage(chatRepository),
            getChatChannelDetail = GetChatChannelDetail(chatRepository),
            getChatChannelDetailMessages = GetChatChannelDetailMessages(chatRepository),
            observeChatChannelMessage = ObserveChatChannelMessage(chatRepository)
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()

    @Provides
    @Singleton
    fun provideAuthUseCases(
        authRepository: AuthRepository,
        sharePrefHelper: SharePrefHelper
    ): AuthUseCases {
        return AuthUseCases(
            signUp = SignUp(authRepository),
            signIn = SignIn(authRepository),
            isSignIn = IsSignIn(authRepository),
            signOut = SignOut(authRepository),
            getRememberMe = GetRememberMe(sharePrefHelper),
            saveRememberMe = SaveRememberMe(sharePrefHelper),
            clearRememberMe = ClearRememberMe(sharePrefHelper),
        )
    }

    @Provides
    @Singleton
    fun provideSharePref(): SharePrefHelper = SharePrefHelperImpl()

}