package com.example.crypto_app.domain.usecases.auth

import android.content.Context
import com.example.crypto_app.domain.manager.SharePrefHelper

class GetRememberMe(private val sharePrefHelper: SharePrefHelper) {
     operator fun invoke (context: Context): String?{
        return sharePrefHelper.getRememberMeValue(context)
    }
}