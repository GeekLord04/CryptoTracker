package com.geekster.cryptotracker.model

data class CryptoResponse(
    val success: Boolean,
    val crypto: Map<String, Crypto>,
    val fiat: Map<String, String>
)