package com.obedcodes.saturn


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EthermineApiService {
    @GET("miner/{minerId}/dashboard")
    fun getMinerStats(
        @Path("minerId") minerId: String
    ): Call<ApiResponse>
}

