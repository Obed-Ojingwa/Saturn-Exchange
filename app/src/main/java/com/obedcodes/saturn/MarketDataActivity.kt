package com.obedcodes.saturn


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.obedcodes.saturn.RetrofitClient

class MarketDataActivity : AppCompatActivity() {

    private lateinit var marketDataTextView: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var lastPrices = mutableMapOf<String, Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_data)

        marketDataTextView = findViewById(R.id.marketDataTextView)

        // Start the periodic data fetching
        startFetchingMarketData()
    }

    private fun startFetchingMarketData() {
        handler.post(object : Runnable {
            override fun run() {
                fetchMarketData()
                handler.postDelayed(this, 30000) // Refresh every 30 seconds
            }
        })
    }

    private fun fetchMarketData() {
        val call = RetrofitClient.cryptoApiService.getCryptoPrices()
        call.enqueue(object : Callback<MarketDataResponse> {
            override fun onResponse(call: Call<MarketDataResponse>, response: Response<MarketDataResponse>) {
                if (response.isSuccessful) {
                    val marketData = response.body()
                    marketData?.let {
                        displayMarketData(it)
                    }
                } else {
                    marketDataTextView.text = "Failed to retrieve market data"
                }
            }

            override fun onFailure(call: Call<MarketDataResponse>, t: Throwable) {
                marketDataTextView.text = "Error: ${t.message}"
            }
        })
    }

    private fun displayMarketData(marketData: MarketDataResponse) {
        val sb = StringBuilder()

        val coins = mapOf(
            "Bitcoin (BTC)" to marketData.bitcoin?.usd,
            "Ethereum (ETH)" to marketData.ethereum?.usd,
            "Ripple (XRP)" to marketData.ripple?.usd,
            "Cardano (ADA)" to marketData.cardano?.usd,
            "Dogecoin (DOGE)" to marketData.dogecoin?.usd,
            "Polkadot (DOT)" to marketData.polkadot?.usd,
            "Chainlink (LINK)" to marketData.chainlink?.usd,
            "Uniswap (UNI)" to marketData.uniswap?.usd,
            "Solana (SOL)" to marketData.solana?.usd,
            "Polygon (MATIC)" to marketData.polygon?.usd
        )

        for ((name, price) in coins) {
            if (price != null) {
                val lastPrice = lastPrices[name] ?: price
                val color = if (price > lastPrice) Color.GREEN else if (price < lastPrice) Color.RED else Color.BLACK

                // Update the last price for comparison in the next update
                lastPrices[name] = price

                sb.append("$name: ").append("$price USD")
                    .append("\n")
                sb.append("\n")
            }
        }

        marketDataTextView.text = sb.toString()
        marketDataTextView.setTextColor(Color.BLACK)
    }
}
