package com.anacoimbra.crackme.domain

import com.anacoimbra.crackme.BuildConfig
import com.anacoimbra.crackme.data.Fact
import retrofit2.http.GET
import retrofit2.http.Headers

interface FactDatasource {

    @Headers("Authorization: ${BuildConfig.AUTH_TOKEN}")
    @GET("facts/random")
    suspend fun getRandomFact(): Fact
}