package com.anacoimbra.crackme.domain

import com.anacoimbra.crackme.BuildConfig
import okhttp3.Cache
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder().apply {
        retryOnConnectionFailure(true)
        cookieJar(CookieJar.NO_COOKIES)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()
}

val retrofit: FactDatasource = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(FactDatasource::class.java)
