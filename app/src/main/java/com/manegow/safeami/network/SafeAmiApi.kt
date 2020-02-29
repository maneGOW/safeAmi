package com.manegow.safeami.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.manegow.safeami.data.request.UserRegistrationRequest
import com.manegow.safeami.data.response.UserRegistrationResponse
import com.manegow.safeami.util.safeApiURL
import com.manegow.safeami.util.urlUserRegister
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(safeApiURL)
    .build()

interface SafeAmiApi{
    @POST(urlUserRegister)
    fun registerUserOnApi(@Body request: UserRegistrationRequest): Call<UserRegistrationResponse>

    object  amiApi{
        val retrofitService : SafeAmiApi by lazy { retrofit.create(SafeAmiApi::class.java) }
    }
}