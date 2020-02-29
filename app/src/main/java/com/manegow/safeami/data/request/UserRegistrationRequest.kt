package com.manegow.safeami.data.request

import com.google.gson.annotations.SerializedName

data class UserRegistrationRequest (
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("device")
    val device: String,
    @SerializedName("deviceModel")
    val deviceModel: String,
    @SerializedName("lastLogin")
    val lastLogin: String,
    @SerializedName("isActive")
    val isActive: Boolean
)
