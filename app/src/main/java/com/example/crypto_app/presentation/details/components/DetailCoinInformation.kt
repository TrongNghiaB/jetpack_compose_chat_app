package com.example.crypto_app.presentation.details.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.example.crypto_app.domain.model.Coin
import com.example.crypto_app.presentation.commons.KeyAndValueField
import com.example.crypto_app.ui.theme.DarkSecondary
import com.example.crypto_app.ui.theme.TextHighLightColor
import com.example.crypto_app.util.Constants
import java.text.DecimalFormat

@Composable
fun DetailCoinInformation(modifier: Modifier = Modifier, coin: Coin) {

    Column {
        val decimalFormat = DecimalFormat("#,###")
        //Statistics
        Text(
            text = "${coin.name} Statistics",
            fontSize = Constants.FontSize20,
            fontWeight = FontWeight.Bold,
            color = TextHighLightColor
        )
        HorizontalDivider(
            modifier = modifier.padding(vertical = Constants.Padding10),
            thickness = Constants.Padding3,
            color = DarkSecondary
        )

        KeyAndValueField(
            keyText = "Market Cap",
            valueText = "$${decimalFormat.format(coin.marketData.marketCap["usd"])}"
        )

        KeyAndValueField(
            keyText = "Fully Diluted Valuation",
            valueText = "$${decimalFormat.format(coin.marketData.fullyDilutedValuation["usd"])}"
        )

        KeyAndValueField(
            keyText = "24 Hour Trading Vol",
            valueText = "$${decimalFormat.format(coin.marketData.totalVolume["usd"])}"
        )

        KeyAndValueField(
            keyText = "Circulating Supply",
            valueText = decimalFormat.format(coin.marketData.circulatingSupply)
        )

        KeyAndValueField(
            keyText = "Total Supply",
            valueText = decimalFormat.format(coin.marketData.totalSupply)
        )

        KeyAndValueField(
            keyText = "Max Supply",
            valueText = decimalFormat.format(coin.marketData.maxSupply)
        )

        Spacer(modifier = modifier.height(Constants.Padding20))

        //Historical Price
        Text(
            text = "${coin.name} Historical Price",
            fontSize = Constants.FontSize20,
            fontWeight = FontWeight.Bold,
            color = TextHighLightColor
        )
        HorizontalDivider(
            modifier = modifier.padding(vertical = Constants.Padding10),
            thickness = Constants.Padding3,
            color = DarkSecondary
        )

        KeyAndValueField(
            keyText = "24H High",
            valueText = "$${coin.marketData.high24H["usd"]}"
        )

        KeyAndValueField(
            keyText = "24H Low",
            valueText = "$${coin.marketData.low24H["usd"]}"
        )

        KeyAndValueField(
            keyText = "All-Time High",
            valueText = "$${coin.marketData.ath["usd"]}"
        )

        KeyAndValueField(
            keyText = "All-Time Low",
            valueText = "$${coin.marketData.atl["usd"]}"
        )

        Spacer(modifier = modifier.height(Constants.Padding20))

        //Info
        Text(
            text = "Info",
            fontSize = Constants.FontSize20,
            fontWeight = FontWeight.Bold,
            color = TextHighLightColor
        )
        HorizontalDivider(
            modifier = modifier.padding(vertical = Constants.Padding10),
            thickness = Constants.Padding3,
            color = DarkSecondary
        )

        KeyAndValueField(
            keyText = "Home Page",
            isLink = true,
            valueText = coin.links.homepage.first()
        )

        KeyAndValueField(
            keyText = "WhitePaper",
            isLink = true,
            valueText = coin.links.whitepaper
        )

        KeyAndValueField(
            keyText = "Blockchain Site",
            isLink = true,
            valueText = coin.links.blockchainSite.first()
        )
    }
}

