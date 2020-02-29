package com.manegow.safeami.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices_table")
data class Device(
    @PrimaryKey(autoGenerate = true)
    var deviceId: Long = 0L,

    @ColumnInfo(name = "deviceName")
    var deviceName: String,

    @ColumnInfo(name = "registrationDate")
    var registrationDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "lastOnline")
    var lastOnline: Long = 0L
)