package com.obedcodes.saturn


// CryptoApiService.kt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {
    @GET("simple/price")
    fun getCryptoPrices(
        @Query("ids") ids: String = "bitcoin,ethereum,ripple,cardano,dogecoin,polkadot,chainlink,uniswap,solana,polygon",
        @Query("vs_currencies") vsCurrencies: String = "usd"
    ): Call<MarketDataResponse>
}

