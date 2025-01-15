package com.example.crypto_app.domain.usecases.auth

import android.content.Context
import com.example.crypto_app.domain.manager.SharePrefHelper

class SaveRememberMe(private val sharePrefHelper: SharePrefHelper) {
    operator fun invoke (context: Context, value: String){
        return sharePrefHelper.saveRememberMeValue(context,value)
    }
}