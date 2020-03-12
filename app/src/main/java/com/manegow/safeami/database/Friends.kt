package com.manegow.safeami.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends_table")
data class Friends(
    @PrimaryKey(autoGenerate = true)
    var friendId: Long = 0L,

    @ColumnInfo(name = "username")
    var friendName: String,

    @ColumnInfo(name = "email")
    var friendEmail: String,

    @ColumnInfo(name = "lastOnline")
    var friendLastOnline: String,

    @ColumnInfo(name = "lastLocation")
    var friendLastLocation: String
)