package com.geekster.cryptotracker.api

import com.geekster.cryptotracker.model.CryptoResponse
import com.geekster.cryptotracker.model.RatesResponse
import com.geekster.cryptotracker.utils.Constants.CRYPTO_INFO
import com.geekster.cryptotracker.utils.Constants.RATES
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

//interface apiMethod {
//
//    @GET(RATES)
//    fun getRates(): Call<RatesResponse>
//
//    @GET(CRYPTO_INFO)
//    fun getCryptoInfo(): Call<CryptoResponse>
//
//}

interface CoinLayerService {
    @GET(RATES)
    suspend fun getRates(): Response<RatesResponse>

    @GET(CRYPTO_INFO)
    suspend fun getCryptoInfo(): Response<CryptoResponse>
}