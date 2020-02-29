package com.manegow.safeami.data.response

import com.google.gson.annotations.SerializedName

data class UserRegistrationResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("description")
    val description: String
)

