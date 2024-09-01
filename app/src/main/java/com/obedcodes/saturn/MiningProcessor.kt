package com.obedcodes.saturn

import java.security.MessageDigest


// this one na to show the mining hash rate, but how can I get a simple Android operating system to get that running?

object MiningProcessor {
    fun mine(data: String, difficulty: Int): String {
        var nonce = 0
        val target = "0".repeat(difficulty)
        var hash: String

        do {
            val input = "$data$nonce"
            hash = sha256(input)
            nonce++
        } while (!hash.startsWith(target))

        return hash
    }

    private fun sha256(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(data.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}
