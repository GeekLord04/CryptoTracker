package com.geekster.cryptotracker.repository

import com.geekster.cryptotracker.api.CoinLayerService
//import com.geekster.cryptotracker.api.apiMethod
import com.geekster.cryptotracker.model.Crypto
import com.geekster.cryptotracker.model.CryptoResponse
import com.geekster.cryptotracker.model.RatesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val service : CoinLayerService) {

    val cachedCryptoData = mutableMapOf<String, Pair<Double, Crypto?>>()

    suspend fun fetchRates(callback: (RatesResponse?) -> Unit) {
        service.getRates().let { response ->
            if (response.isSuccessful) {
                callback(response.body())
            } else {
                callback(null)
            }
        }
    }

    suspend fun fetchCryptoInfo(symbol: String, callback: (CryptoResponse?) -> Unit) {
        service.getCryptoInfo().let { response ->
            if (response.isSuccessful) {
                val crypto = response.body()?.crypto?.get(symbol)
                callback(response.body())
            } else {
                callback(null)
            }
        }
    }

    fun getCachedData(): Map<String, Pair<Double, Crypto?>> {
        return cachedCryptoData.toMap()
    }
}