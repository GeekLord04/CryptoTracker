package com.geekster.cryptotracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekster.cryptotracker.model.Crypto
import com.geekster.cryptotracker.repository.CryptoRepository
import com.geekster.cryptotracker.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptoData = MutableLiveData<Map<String, Pair<Double, Crypto?>>>()
    val cryptoData: LiveData<Map<String, Pair<Double, Crypto?>>>
        get() = _cryptoData

    private val viewModelScope = CoroutineScope(Dispatchers.Default)
    suspend fun fetchCryptoData() {
        viewModelScope.launch {
            coroutineScope {
                val cachedData = repository.getCachedData()
                if (cachedData.isEmpty()) {
                    repository.fetchRates { ratesResponse ->
                        ratesResponse?.let { rates ->
                            val ratesMap = rates.rates

                            //For loop
                            ratesMap.keys.forEach { symbol ->
                                //Log.d("Check Size", "fetchCryptoData: $symbol")
                                viewModelScope.launch(Dispatchers.IO) {
                                    repository.fetchCryptoInfo(symbol) { cryptoResponse ->
                                        val crypto = cryptoResponse?.crypto?.get(symbol)
                                        val rate = ratesMap[symbol]

//                                        Log.d("Check Size", "fetchCryptoData: $crypto")

                                        repository.cachedCryptoData[symbol] = Pair(rate ?: 0.0, crypto)

                                        if (repository.cachedCryptoData.size == ratesMap.size) {
                                            _cryptoData.postValue(repository.getCachedData())
                                        }
                                    }
//                            Log.d("Check", "ViewModel is $i")

                                }
                            }
//                            Log.d("Check Size", "Size is ${ratesMap.keys}")
//                    repository.fetchCryptoInfo()
                        }
                    }
                } else {
                    _cryptoData.postValue(cachedData)
                }
            }
        }
    }

    var i = 0;

}