package com.example.crypto_app.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.domain.usecases.coins.CoinsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCoinViewModel @Inject constructor(private val coinsUseCases: CoinsUseCases):
    ViewModel() {
    var coin: Coin? by mutableStateOf(null)

    var isLoading by mutableStateOf(false)
        private set

    fun getCoinDetail(coinId: String) {
        isLoading = true
        viewModelScope.launch {
            coin = coinsUseCases.getCoinDetail(coinId)
            isLoading = false
        }
    }
}