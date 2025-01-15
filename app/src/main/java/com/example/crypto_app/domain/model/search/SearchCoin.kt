package com.example.crypto_app.domain.model.search


import com.google.gson.annotations.SerializedName

data class SearchCoin(
    @SerializedName("api_symbol")
    val apiSymbol: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("large")
    val large: String,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumb")
    val thumb: String
)