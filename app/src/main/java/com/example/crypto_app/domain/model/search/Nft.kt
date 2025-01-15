package com.example.crypto_app.domain.model.search


import com.google.gson.annotations.SerializedName

data class Nft(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("thumb")
    val thumb: String
)