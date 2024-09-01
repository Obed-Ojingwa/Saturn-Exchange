package com.obedcodes.saturn

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EthermineRetrofitClient {
    private const val BASE_URL = "https://api.ethermine.org/"


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val ethermineApiService: EthermineApiService by lazy {
        retrofit.create(EthermineApiService::class.java)
    }
}

