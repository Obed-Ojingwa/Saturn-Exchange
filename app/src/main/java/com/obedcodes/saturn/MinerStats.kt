package com.obedcodes.saturn


data class MinerStats(
    val reportedHashrate: Long,
    val currentHashrate: Long,
    val validShares: Int,
    val invalidShares: Int,
    val staleShares: Int,
    val activeWorkers: Int,
    val unpaid: Long,
    val coinsPerMin: Double,
    val usdPerMin: Double,
    val btcPerMin: Double
)




data class ApiResponse(
    val status: String,
    val data: MinerStats?
)


// CryptoPrice.kt
data class CryptoPrice(
    val usd: Double
)


data class MarketDataResponse(
    val bitcoin: CryptoPrice?,
    val ethereum: CryptoPrice?,
    val ripple: CryptoPrice?,
    val cardano: CryptoPrice?,
    val dogecoin: CryptoPrice?,
    val polkadot: CryptoPrice?,
    val chainlink: CryptoPrice?,
    val uniswap: CryptoPrice?,
    val solana: CryptoPrice?,
    val polygon: CryptoPrice?
)
data class Coin(
    val usd: Double
)

