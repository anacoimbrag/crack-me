package com.anacoimbra.crackme.domain

import com.anacoimbra.crackme.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BuildConfig.VTEX_API_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(T::class.java)

private val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder().apply {
        retryOnConnectionFailure(true)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()
}