package com.example.crypto_app.domain.model


import com.google.gson.annotations.SerializedName

data class CoinItem(
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("high_24h")
    val high24h: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("low_24h")
    val low24h: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("symbol")
    val symbol: String,
) {

    fun percentage24hDifference(): String {
        val isCoinPriceIncrease = priceChange24h >= 0.0;
        return if(isCoinPriceIncrease){
            "+" + String.format("%.2f",priceChangePercentage24h ) + "%"
        } else {
            "-" + String.format("%.2f",priceChangePercentage24h ) + "%"
        }
    }
}