package com.example.crypto_app.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.crypto_app.domain.manager.SharePrefHelper
import com.example.crypto_app.util.Constants.PREFS_NAME
import com.example.crypto_app.util.Constants.REMEMBER_ME

class SharePrefHelperImpl : SharePrefHelper {
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            // Log and handle the error appropriately
            Log.e("SharedPreferencesError", "Error while creating encrypted SharedPreferences", e)
            // Fallback to regular SharedPreferences or handle the error as needed
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    override fun saveRememberMeValue(context: Context, value: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(REMEMBER_ME, value).apply()
    }

    override fun getRememberMeValue(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(REMEMBER_ME, null)
    }

    override fun clearCredentials(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().apply {
            remove(REMEMBER_ME)
            apply()
        }
    }
}