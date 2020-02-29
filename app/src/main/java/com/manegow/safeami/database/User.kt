package com.manegow.safeami.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = " registrationDate")
    var registrationDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "lastLogin")
    var lastLogig: Long = 0L
)