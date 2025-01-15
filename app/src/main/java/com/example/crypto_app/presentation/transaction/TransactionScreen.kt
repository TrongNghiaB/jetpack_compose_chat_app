package com.example.crypto_app.presentation.transaction

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.example.crypto_app.R
import com.example.crypto_app.presentation.authentication.common_auth.CommonAuthTextField
import com.example.crypto_app.presentation.commons.CalenderPicker
import com.example.crypto_app.presentation.profile.ProfileViewModel
import com.example.crypto_app.util.Constants
import com.example.crypto_app.util.Constants.Padding15
import com.example.crypto_app.util.Constants.Padding56
import com.example.crypto_app.util.DateUtil
import java.security.Key

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Buy", "Sell")
    val focusManager = LocalFocusManager.current
    focusManager.clearFocus()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = Constants.Padding10)
    ) {
        if (profileViewModel.showCalenderPickerPopup) {
            CalenderPicker(
                onDateSelected = {
                    if(it == null) return@CalenderPicker
                    val formattedDate = DateUtil.convertMillisToDate(it)
                    profileViewModel.datePickerTextField = formattedDate
                },
                onDismiss = { profileViewModel.setCalendarPickerPopup(false) }
            )
        }
        Text(
            "Add Transaction",
            color = Color.White,
            fontSize = Constants.FontSize30,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(Constants.Padding20))

        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.Green
                )
            },
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            title,
                            color = if (selectedTabIndex == index) Color.White else Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> AddCoinTransactionsPopupTextField(profileViewModel = profileViewModel)
            1 -> AddCoinTransactionsPopupTextField(
                profileViewModel = profileViewModel,
                isSell = true
            )
        }

        Row(horizontalArrangement = Arrangement.Center) {
            Button(
                modifier = modifier
                    .width(Constants.Padding100),
                colors = ButtonDefaults.buttonColors(Color.Red),
                onClick = onBackClick
            ) {
                Text("Cancel", color = Color.White)
            }
            Spacer(modifier = Modifier.width(Constants.Padding20))
            Button(
                modifier = modifier
                    .width(Constants.Padding100),
                colors = ButtonDefaults.buttonColors(Color.Green),
                onClick = {
                    profileViewModel.updatePortfolioCoin()
                }) {
                Text("Save")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddCoinTransactionsPopupTextField(
    modifier: Modifier = Modifier, profileViewModel: ProfileViewModel, isSell: Boolean = false
) {
    Column {
        Spacer(modifier = Modifier.height(Constants.Padding10))
        CommonAuthTextField(
            title = "Coin Chosen",
            value = profileViewModel.coinEdit?.name ?: "Coin Name",
            readOnly = true,
            leadingIcon = {
                AsyncImage(
                    model = profileViewModel.coinEdit?.image ?: "",
                    contentDescription = "coin image",
                    error = painterResource(id = R.drawable.ic_error),
                    placeholder = painterResource(id = R.drawable.ic_currency_bitcoin),
                    modifier = Modifier.size(Constants.Padding24),
                )
            },

        )
        Spacer(modifier = Modifier.height(Constants.Padding10))
        CommonAuthTextField(
            title = "Price per coin",
            titleColor = Color.White,
            value = profileViewModel.priceTextField,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                profileViewModel.priceTextField = it
                profileViewModel.transactionAutoCalculate()
            },
            trailingIcon = {
                Text(
                    "USD",
                    color = Color.White.copy(alpha = 0.5f),
                    fontWeight = FontWeight.W700
                )
            }
        )
        Spacer(modifier = Modifier.height(Constants.Padding10))
        CommonAuthTextField(
            title = "Amount",
            titleColor = Color.White,
            value = profileViewModel.amountTextField,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                profileViewModel.amountTextField = it
                profileViewModel.transactionAutoCalculate()
            },
            trailingIcon = {
                Text(
                    "${profileViewModel.coinEdit?.symbol?.uppercase()}",
                    color = Color.White.copy(alpha = 0.5f),
                    fontWeight = FontWeight.W700
                )
            }
        )
        Spacer(modifier = Modifier.height(Constants.Padding10))
        CommonAuthTextField(
            title = "Total ${if (isSell) "Received" else "Expense"}",
            titleColor = Color.White,
            value = profileViewModel.totalExpenseTextField,
            readOnly = true,
            trailingIcon = {
                Text(
                    "USD",
                    color = Color.White.copy(alpha = 0.5f),
                    fontWeight = FontWeight.W700
                )
            }
        )
        Spacer(modifier = Modifier.height(Constants.Padding10))
        Text(
            text = "Date time",
            fontSize = Constants.FontSize15,
            color = Color.White
        )
        Spacer(modifier = modifier.height(Constants.Padding5))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(Padding56)
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(Constants.Padding10),
                )
                .padding(horizontal = Padding15)
                .clickable {
                    profileViewModel.setCalendarPickerPopup(true)
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = profileViewModel.datePickerTextField)
        }
        Spacer(modifier = Modifier.height(Constants.Padding10))
    }
}