package com.manegow.safeami.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SafeAmiDatabaseDao {

    //------------------------- users ----------------------
    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * from users_table WHERE userId = :userKey")
    fun getUser(userKey: Long): User

    @Query("DELETE FROM users_table")
    fun clearUsers()

    //------------------------- devices ----------------------
    @Insert
    fun insertDevice(device: Device)

    @Update
    fun updateDevice(device: Device)

    @Query("SELECT * FROM devices_table WHERE deviceId = :deviceKey")
    fun getDevice(deviceKey: Long): Device

    @Query("SELECT * FROM devices_table ORDER BY registrationDate DESC")
    fun getAllDevices(): LiveData<List<Device>>

    @Delete
    fun deleteDevice(device: Device)

    @Query("DELETE FROM devices_table")
    fun deleteAllDevices()

    //------------------------- Friends ----------------------

    @Insert
    fun insertNewFriend(friend: Friends)

    @Update
    fun updateFriend(friend: Friends)

    @Query("SELECT * FROM friends_table WHERE friendId = :friendKey")
    fun getFriend(friendKey: Long): Friends

    @Query("SELECT * FROM friends_table")
    fun getAllFriends(): LiveData<List<Friends>>

    @Delete
    fun deleteFriend(friend: Friends)

    @Query("DELETE FROM friends_table")
    fun deleteAllFriends()

    //------------------------- Locations ----------------------

    @Insert
    fun insertLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

    @Query("SELECT * FROM locations_table ORDER BY registrationDate DESC")
    fun getAllLocations(): LiveData<List<Location>>

    @Query("SELECT * FROM locations_table WHERE fromDevice = :deviceKey ORDER BY registrationDate DESC")
    fun getLocationFromDevice(deviceKey: Long): LiveData<List<Location>>

    @Query("SELECT * FROM locations_table WHERE fromDevice = :deviceKey AND registrationDate = :date ORDER BY registrationDate DESC")
    fun getLocationFromDeviceAndDate(deviceKey: Long, date: Long): LiveData<List<Location>>

    @Query("SELECT * FROM locations_table WHERE registrationDate = :date ORDER BY registrationDate DESC")
    fun getLocationFromDate(date: Long): LiveData<List<Location>>

    @Query("DELETE FROM locations_table")
    fun deleteAllLocations()

}