package com.example.crypto_app.domain.manager

import android.content.Context


interface SharePrefHelper {
    fun saveRememberMeValue(context: Context, value: String)

    fun getRememberMeValue(context: Context): String?

    fun clearCredentials(context: Context)
}