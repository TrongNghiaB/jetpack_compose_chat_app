package com.example.crypto_app.di

import android.app.Application
import androidx.room.Room
import com.example.crypto_app.data.local.CryptoDao
import com.example.crypto_app.data.local.CryptoDatabase
import com.example.crypto_app.data.local.CryptoTypeConverter
import com.example.crypto_app.data.local.SharePrefHelperImpl
import com.example.crypto_app.data.remote.CoinAPI
import com.example.crypto_app.data.repository.AuthRepositoryImpl
import com.example.crypto_app.data.repository.ChatRepositoryImpl
import com.example.crypto_app.data.repository.CoinRepositoryImpl
import com.example.crypto_app.domain.manager.SharePrefHelper
import com.example.crypto_app.domain.repository.AuthRepository
import com.example.crypto_app.domain.repository.ChatRepository
import com.example.crypto_app.domain.repository.CoinsRepository
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
import com.example.crypto_app.domain.usecases.coins.CoinsUseCases
import com.example.crypto_app.domain.usecases.coins.DeleteUser
import com.example.crypto_app.domain.usecases.coins.GetAllMarketCoins
import com.example.crypto_app.domain.usecases.coins.GetAllUsers
import com.example.crypto_app.domain.usecases.coins.GetCoinDetail
import com.example.crypto_app.domain.usecases.coins.GetCurrentUserEmail
import com.example.crypto_app.domain.usecases.coins.GetCurrentUserId
import com.example.crypto_app.domain.usecases.coins.GetUserByID
import com.example.crypto_app.domain.usecases.coins.UpsertUserInfo
import com.example.crypto_app.domain.usecases.coins.SearchCoins
import com.example.crypto_app.util.Constants.BASE_URL
import com.example.crypto_app.util.Constants.COINS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCoinApi(): CoinAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinsRepository(coinAPI: CoinAPI, cryptoDao: CryptoDao): CoinsRepository =
        CoinRepositoryImpl(coinAPI, cryptoDao)

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository = ChatRepositoryImpl()

    @Provides
    @Singleton
    fun provideChatUseCases(chatRepository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            searchUser = SearchUsers(chatRepository),
            getAllUsers = GetAllUsers(chatRepository),
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
    fun provideCoinsUseCases(coinsRepository: CoinsRepository): CoinsUseCases {
        return CoinsUseCases(
            getAllMarketCoins = GetAllMarketCoins(coinsRepository),
            getCoinDetail = GetCoinDetail(coinsRepository),
            searchCoin = SearchCoins(coinsRepository),
            upsertUserInfo = UpsertUserInfo(coinsRepository),
            deleteUser = DeleteUser(coinsRepository),
            getUserByID = GetUserByID(coinsRepository),
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository =
        AuthRepositoryImpl()

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
            getCurrentUserId = GetCurrentUserId(authRepository),
            getCurrentUserEmail = GetCurrentUserEmail(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCryptoDatabase(application: Application): CryptoDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = CryptoDatabase::class.java,
            name = COINS_DATABASE_NAME
        ).addTypeConverter(CryptoTypeConverter())
            .fallbackToDestructiveMigration() //use for room migration if any update
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoDao(cryptoDatabase: CryptoDatabase): CryptoDao = cryptoDatabase.cryptoDao

    @Provides
    @Singleton
    fun provideSharePref(): SharePrefHelper = SharePrefHelperImpl()

}