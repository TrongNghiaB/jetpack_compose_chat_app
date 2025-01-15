package com.example.crypto_app.domain.model.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize //add this annotate to send this object through nav graph
@Entity
data class User(
    @PrimaryKey
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("totalBalance")
    val totalBalance: Double,
    @SerializedName("listUserCoin")
    val listFavoriteCoin: List<UserCoin>,
    @SerializedName("portfolio")
    val portfolio: List<UserCoin>
) : Parcelable


@Parcelize //add this annotate to send this object through nav graph
@Entity
data class ChatUser(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String?,
) : Parcelable

