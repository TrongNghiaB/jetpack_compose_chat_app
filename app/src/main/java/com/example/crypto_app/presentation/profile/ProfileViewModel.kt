package com.example.crypto_app.presentation.profile

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_app.domain.model.profile.User
import com.example.crypto_app.domain.model.profile.UserCoin
import com.example.crypto_app.domain.usecases.auth.AuthUseCases
import com.example.crypto_app.domain.usecases.coins.CoinsUseCases
import com.example.crypto_app.util.DateUtil
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val coinsUseCases: CoinsUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    var imageUri by mutableStateOf<Uri?>(null)
        private set
    var user by mutableStateOf<User?>(null)
        private set
    var showEditProfilePopup by mutableStateOf(false)
        private set
    var showCalenderPickerPopup by mutableStateOf(false)
        private set
    var userNameTextField by mutableStateOf("")
        private set
    var totalExpenseTextField by mutableStateOf("")
    var amountTextField by mutableStateOf("")
    var priceTextField by mutableStateOf("")

    var datePickerTextField: String by mutableStateOf(DateUtil.getLocalDateTimeNowString())

    var portfolioCoins = mutableStateListOf<UserCoin>()
        private set
    var coinEdit by mutableStateOf<UserCoin?>(null)
        private set

    fun onImagePicked(uri: Uri?) {
        imageUri = uri
    }

    init {
        authUseCases.getCurrentUserEmail()?.let {
            viewModelScope.launch {
                user = coinsUseCases.getUserByID(it)
                user?.let {
                    userNameTextField = it.name
                    imageUri = Uri.parse(it.image)
                    portfolioCoins.addAll(it.listFavoriteCoin)
                }
            }
        }
    }

    fun onUserNameValueChange(value: String) {
        userNameTextField = value
    }

    fun updateUserInformation(
        totalBalance: Double? = null,
        listFavoriteCoin: List<UserCoin>? = null,
        portfolio: List<UserCoin>? = null
    ) {
        if (user != null) {
            val newUser = user!!.copy(
                name = userNameTextField,
                image = if (imageUri != null) imageUri.toString() else user!!.image,
                totalBalance = totalBalance ?: user!!.totalBalance,
                listFavoriteCoin = listFavoriteCoin ?: user!!.listFavoriteCoin,
                portfolio = portfolio ?: user!!.portfolio
            )
            viewModelScope.launch {
                coinsUseCases.upsertUserInfo(newUser)
            }
            user = newUser
        }
    }

    fun editProfilePopUpState(isShow: Boolean) {
        showEditProfilePopup = isShow
    }

    fun setCalendarPickerPopup(isShow: Boolean) {
        showCalenderPickerPopup = isShow
    }

    fun setCoinTransaction(coin: UserCoin? = null) {
        coinEdit = coin
    }

    fun transactionAutoCalculate() {
        val value1 = amountTextField.toDoubleOrNull()
        val value2 = priceTextField.toDoubleOrNull()

        if (value1 != null && value2 != null) {
            totalExpenseTextField = (value1 * value2).toString()
        }
    }


    fun updatePortfolioCoin(isSell: Boolean = false) {
        if (user == null || coinEdit == null) return

        val currentTotalBalance = user!!.totalBalance

        val currentCoinTotalExpense = totalExpenseTextField.toDoubleOrNull() ?: 0.0
        var selectedCoinAvgBuyCost: Double = coinEdit!!.averageBuyCost

        if (isSell) {
            if (selectedCoinAvgBuyCost > 0 && selectedCoinAvgBuyCost > currentCoinTotalExpense) {
                selectedCoinAvgBuyCost -= currentCoinTotalExpense
            } else {
                selectedCoinAvgBuyCost = 0.0
            }

        } else {
            selectedCoinAvgBuyCost += currentCoinTotalExpense
        }

        val editedCoin = coinEdit!!.copy(averageBuyCost = selectedCoinAvgBuyCost)
        val editedPortfolio = portfolioCoins.map {
            if (it.id == editedCoin.id) editedCoin else it
        }

        viewModelScope.launch {
            updateUserInformation(
                totalBalance = 0.0,
                portfolio = editedPortfolio
            )
        }

        authUseCases.getCurrentUserEmail()?.let { id ->
            viewModelScope.launch {
                user = coinsUseCases.getUserByID(id)
                user?.let {
                    userNameTextField = it.name
                    imageUri = Uri.parse(it.image)
                    portfolioCoins.addAll(it.listFavoriteCoin)
                }
            }
        }
    }
}

