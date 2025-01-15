package com.example.crypto_app.domain.model.search


import com.google.gson.annotations.SerializedName

data class Exchange(
    @SerializedName("id")
    val id: String,
    @SerializedName("large")
    val large: String,
    @SerializedName("market_type")
    val marketType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumb")
    val thumb: String
)