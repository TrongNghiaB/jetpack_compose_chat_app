package com.example.crypto_app.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class DateUtil {
    companion object {
        fun convertMillisToDate(dateMillis: Long): String {
            val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = dateMillis
            return formatter.format(calendar.time)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getLocalDateTimeNowString(): String {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return currentDateTime.format(formatter)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getLocalDateTimeNowToMilliseconds(): Long {
            val now = LocalDateTime.now()
            val milliseconds = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            return milliseconds
        }
    }
}