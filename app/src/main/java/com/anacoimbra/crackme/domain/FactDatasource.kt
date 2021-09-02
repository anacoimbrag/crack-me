package com.anacoimbra.crackme.domain

import com.anacoimbra.crackme.BuildConfig
import com.anacoimbra.crackme.data.Fact
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FactDatasource {

    @Headers("Authorization: ${BuildConfig.AUTH_TOKEN}")
    @GET("facts/random")
    suspend fun getRandomFact(@Query("animal_type") type: String): Fact
}