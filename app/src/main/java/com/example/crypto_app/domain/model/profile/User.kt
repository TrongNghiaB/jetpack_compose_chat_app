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
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String?,
) : Parcelable

