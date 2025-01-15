package com.example.crypto_app.domain.model.profile

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize //add this annotate to send this object through nav graph
@Entity
data class UserCoin(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("averageBuyCost")
    val averageBuyCost: Double = 0.0
): Parcelable