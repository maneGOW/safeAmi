package com.manegow.safeami.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations_table")
data class Location(
    @PrimaryKey(autoGenerate = true)
    var locationId: Long = 0L,

    @ColumnInfo(name = "latitude")
    var latitude: Long,

    @ColumnInfo(name = "longitude")
    var longitude: Long,

    @ColumnInfo(name = "registrationDate")
    var registrationDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name= "fromDevice")
    var ownerId: Long
)