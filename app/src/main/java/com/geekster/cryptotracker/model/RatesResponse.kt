package com.geekster.cryptotracker.model

data class RatesResponse(
    val privacy: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val target: String,
    val terms: String,
    val timestamp: Long
)