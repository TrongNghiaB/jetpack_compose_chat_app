package com.example.crypto_app.util

import android.content.Context
import android.widget.Toast

object ToastManager {
    private var currentToast: Toast? = null

    fun showToast(context: Context, message: String) {
        currentToast?.cancel()
        currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
            show()
        }
    }
}