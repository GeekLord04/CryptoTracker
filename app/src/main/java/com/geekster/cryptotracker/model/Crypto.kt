package com.geekster.cryptotracker.model

data class Crypto(
    val symbol: String,
    val name: String,
    val name_full: String,
    val maxSupply: Long,
    val icon_url: String
)
