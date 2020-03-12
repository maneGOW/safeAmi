package com.manegow.safeami.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [User::class, Device::class, Location::class, Friends::class],
    version = 1,
    exportSchema = false
)
abstract class SafeAmiDatabase : RoomDatabase() {

    abstract fun safeAmiDatabaseDao(): SafeAmiDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SafeAmiDatabase? = null

        fun getInstance(context: Context): SafeAmiDatabase {
            synchronized(SafeAmiDatabase::class.java) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SafeAmiDatabase::class.java,
                        "safeami_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}