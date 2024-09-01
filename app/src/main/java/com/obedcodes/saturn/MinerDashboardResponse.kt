package com.obedcodes.saturn

data class MinerDashboardResponse(
    val status: String,
    val data: MinerData
)

data class MinerData(
    val currentStatistics: CurrentStatistics,
    val workers: List<Worker>
)

data class CurrentStatistics(
    val reportedHashrate: Double,
    val currentHashrate: Double,
    val validShares: Int,
    val staleShares: Int,
    val activeWorkers: Int
)

data class Worker(
    val worker: String,
    val time: Long,
    val lastSeen: Long,
    val reportedHashrate: Double,
    val currentHashrate: Double,
    val validShares: Int
)
