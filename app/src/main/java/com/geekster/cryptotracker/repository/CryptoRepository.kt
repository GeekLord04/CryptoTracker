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

//    fun fetchRates(callback: (RatesResponse?) -> Unit) {
//        service.getRates().enqueue(object : Callback<RatesResponse> {
//            override fun onResponse(call: Call<RatesResponse>, response: Response<RatesResponse>) {
//                if (response.isSuccessful) {
//                    callback(response.body())
//                } else {
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<RatesResponse>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }
//
//    fun fetchCryptoInfo(symbol: String, callback: (CryptoResponse?) -> Unit) {
//        service.getCryptoInfo().enqueue(object : Callback<CryptoResponse> {
//            override fun onResponse(call: Call<CryptoResponse>, response: Response<CryptoResponse>) {
//                if (response.isSuccessful) {
//                    callback(response.body())
//                } else {
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<CryptoResponse>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }

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