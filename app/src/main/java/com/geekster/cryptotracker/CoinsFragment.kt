package com.geekster.cryptotracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekster.cryptotracker.databinding.FragmentCoinsBinding
import com.geekster.cryptotracker.databinding.FragmentFirstBinding
import com.geekster.cryptotracker.utils.Constants
import com.geekster.cryptotracker.utils.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("DEPRECATION")
@AndroidEntryPoint
class CoinsFragment : Fragment() {

    private var _binding : FragmentCoinsBinding? = null
    private val binding get() = _binding!!

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var cryptoAdapter: CryptoAdapter

    private val cryptoViewModel by viewModels<CryptoViewModel>()

    //Fetch after 3 mins
    private val handler = Handler(Looper.getMainLooper())
    private val fetchCryptoDataRunnable = object : Runnable {
        override fun run() {
            lifecycleScope.launch(Dispatchers.IO) {
                val currentTime = System.currentTimeMillis()
                Log.d(TAG, "Fetching crypto data at ${getTimeString(currentTime)}")
                cryptoViewModel.fetchCryptoData()
                binding.timeFetched.text = "Last fetched at ${getTimeString(currentTime)}"
            }
            // Schedule the next execution in 3 minutes
            handler.postDelayed(this, 3 * 60 * 1000) // 3 minutes in milliseconds
        }
    }

    private fun getTimeString(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return sdf.format(calendar.time)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCoinsBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoAdapter = CryptoAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = cryptoAdapter



        Toast.makeText(context,"Do you know you can pull down to refresh?",Toast.LENGTH_SHORT).show()



        observeCrypto()

        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.IO) {
                //new
                val currentTime = System.currentTimeMillis()
                Log.d("Time", "Fetching crypto data at ${getTimeString(currentTime)}")
                binding.timeFetched.text = "Last fetched at ${getTimeString(currentTime)}"
                cryptoViewModel.fetchCryptoData()
            }

        }
        lifecycleScope.launch(Dispatchers.IO) {
            //new
            val currentTime = System.currentTimeMillis()
            Log.d("Time", "Fetching crypto data at ${getTimeString(currentTime)}")
            binding.timeFetched.text = "Last fetched at ${getTimeString(currentTime)}"
            cryptoViewModel.fetchCryptoData()
        }

        //new
        handler.postDelayed(fetchCryptoDataRunnable, 3 * 60 * 1000)



    }

    private fun observeCrypto() {
        cryptoViewModel.cryptoData.observe(viewLifecycleOwner) { cryptoData ->
            val updatedList = cryptoData.toList()
            if (binding.recyclerView.adapter == null) {
                cryptoAdapter = CryptoAdapter(updatedList)
                binding.recyclerView.adapter = cryptoAdapter
                binding.swipeRefresh.isRefreshing = false

            } else {
                cryptoAdapter?.setData(updatedList)
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}