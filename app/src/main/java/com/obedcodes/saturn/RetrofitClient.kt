package com.obedcodes.saturn


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    /*The Retro insyance */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val cryptoApiService: CryptoApiService by lazy {
        retrofit.create(CryptoApiService::class.java)
    }
}

object RetrofitClient2 {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    val cryptoApiService: CryptoApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoApiService::class.java)
}
