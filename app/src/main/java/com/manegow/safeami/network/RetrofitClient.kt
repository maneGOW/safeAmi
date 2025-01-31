package com.manegow.safeami.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    var retrofit: Retrofit? = null

    fun getClient(baseURL: String): Retrofit?{
        if(retrofit == null){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor{
                    chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("access-token", "accesstokenifnecesary")
                        .header("client", "ifnecesary")
                        .header("expiry", "ifnecesary")
                        .header("uid", "ifnecesary")
                        .method(original.method, original.body)
                        .build()
                    return@Interceptor chain.proceed(request)
                })
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}