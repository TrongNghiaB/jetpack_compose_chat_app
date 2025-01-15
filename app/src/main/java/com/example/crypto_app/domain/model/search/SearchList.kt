package com.example.crypto_app.domain.model.search


import com.google.gson.annotations.SerializedName

data class SearchList(
    @SerializedName("coins")
    val searchCoins: List<SearchCoin>,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>,
    @SerializedName("nfts")
    val nfts: List<Nft>
)