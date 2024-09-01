package com.obedcodes.saturn

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MiningActivity : AppCompatActivity() {

    private lateinit var minerIdEditText: EditText
    private lateinit var fetchStatsButton: Button
    private lateinit var statsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mining)

        minerIdEditText = findViewById(R.id.minerIdEditText)
        fetchStatsButton = findViewById(R.id.fetchStatsButton)
        statsTextView = findViewById(R.id.statsTextView)

        fetchStatsButton.setOnClickListener {
            val minerId = minerIdEditText.text.toString().trim()
            if (minerId.isNotEmpty()) {
                fetchMinerStats(minerId)
            }
        }
    }

    private fun fetchMinerStats(minerId: String) {
        val call = EthermineRetrofitClient.ethermineApiService.getMinerStats(minerId)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val minerStats = response.body()?.data
                    minerStats?.let {
                        displayStats(it)
                    }
                } else {
                    statsTextView.text = "Failed to retrieve data"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                statsTextView.text = "Error: ${t.message}"
            }
        })
    }

    private fun displayStats(minerStats: MinerStats) {
        statsTextView.text = """
            Reported Hashrate: ${minerStats.reportedHashrate}
            Current Hashrate: ${minerStats.currentHashrate}
            Valid Shares: ${minerStats.validShares}
            Invalid Shares: ${minerStats.invalidShares}
            Stale Shares: ${minerStats.staleShares}
            Active Workers: ${minerStats.activeWorkers}
            Unpaid: ${minerStats.unpaid}
            Coins Per Minute: ${minerStats.coinsPerMin}
            USD Per Minute: ${minerStats.usdPerMin}
            BTC Per Minute: ${minerStats.btcPerMin}
        """.trimIndent()
    }
}
