package com.example.crypto_app.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.model.CoinList
import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.model.profile.UserCoin
import com.example.crypto_app.domain.usecases.auth.AuthUseCases
import com.example.crypto_app.domain.usecases.coins.CoinsUseCases
import com.example.crypto_app.domain.usecases.coins.SortingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val coinsUseCases: CoinsUseCases, private val authUseCases: AuthUseCases) : ViewModel() {
    private val reloadList = mutableListOf(0)
    private var user by mutableStateOf<User?>(null)

    var selectedTabIndex by mutableIntStateOf(0)
        private set

    var favoriteCoins = mutableStateListOf<UserCoin>()
        private set

    var allMarketCoins by mutableStateOf(CoinList())
        private set
    var gainerCoins by mutableStateOf(CoinList())
        private set
    var loserCoins by mutableStateOf(CoinList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    val homeTabs = listOf("Favorites", "Hot", "Gainers", "Losers")

    init {
        viewModelScope.launch {
            authUseCases.getCurrentUserEmail()?.let {
                coinsUseCases.getUserByID(it)?.let {userInfo ->
                    user = userInfo
                    favoriteCoins.addAll(userInfo.listFavoriteCoin)
                }
            }
        }
    }

    fun changeTab(index: Int) {
        selectedTabIndex = index
        if (index != 0 && !reloadList.contains(index)) {
            reloadList.add(index)
            getAllMarketCoins(index)
        }
    }

    fun getAllMarketCoins(index: Int) {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            loadCoinList(index)
            isLoading = false
        }
    }

    fun refreshHome(){
        isRefreshing = true
        viewModelScope.launch (Dispatchers.IO){
            loadCoinList(selectedTabIndex)
            for (i in 0..3) {
                if (!reloadList.contains(i)) {
                    reloadList.add(i)
                }
            }
            isRefreshing = false
        }
    }

    fun updateListCoinFavorites(coin: UserCoin){
        if(favoriteCoins.contains(coin)) {
            favoriteCoins.remove(coin)
            viewModelScope.launch {
                updateUserCoinList()
            }
        } else {
            favoriteCoins.add(coin)
            viewModelScope.launch {
                updateUserCoinList()
            }
        }
    }

    private suspend fun updateUserCoinList() {
        user?.let {
            val userUpsert = it.copy(listFavoriteCoin = favoriteCoins)
            coinsUseCases.upsertUserInfo(userUpsert)
        }
    }

     private suspend fun loadCoinList(index: Int){
           when (index) {
               1 -> allMarketCoins = coinsUseCases.getAllMarketCoins(SortingOrder.MarketCapDesc)
               2 -> gainerCoins = coinsUseCases.getAllMarketCoins(SortingOrder.VolumeDesc)
               3 -> loserCoins = coinsUseCases.getAllMarketCoins(SortingOrder.VolumeAsc)
           }
    }
}