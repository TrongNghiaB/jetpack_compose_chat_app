package com.example.crypto_app.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.crypto_app.domain.model.profile.UserCoin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class CryptoTypeConverter {
    private val gson = Gson()
    private val type = object : TypeToken<List<UserCoin>>() {}.type

    @TypeConverter
    fun userCoinListToString(userCoinList: List<UserCoin>): String {
        return gson.toJson(userCoinList, type)
    }

    @TypeConverter
    fun stringToUserCoinList(value: String): List<UserCoin> {
        return gson.fromJson(value, type)
    }
}