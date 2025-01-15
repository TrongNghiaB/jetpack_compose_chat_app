package com.example.crypto_app.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.crypto_app.domain.model.profile.User

@Database(
    entities = [User::class],
    version = 4,
//    autoMigrations = [AutoMigration(3,4)]
)
@TypeConverters(CryptoTypeConverter::class)
abstract class CryptoDatabase: RoomDatabase() {
    abstract val cryptoDao: CryptoDao
}