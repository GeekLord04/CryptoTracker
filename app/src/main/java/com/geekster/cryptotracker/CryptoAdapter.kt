package com.geekster.cryptotracker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekster.cryptotracker.databinding.ListItemBinding
import com.geekster.cryptotracker.model.Crypto
import com.geekster.cryptotracker.utils.Constants.TAG

class CryptoAdapter(private var cryptoList: List<Pair<String, Pair<Double, Crypto?>>>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val (symbol, data) = cryptoList[position]
        val rate = data.first
        val crypto = data.second

        holder.bind(symbol, rate, crypto)
    }
    fun setData(updatedList: List<Pair<String, Pair<Double, Crypto?>>>) {
        cryptoList = updatedList
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = cryptoList.size

    class CryptoViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(symbol: String, rate: Double, crypto: Crypto?) {
            var rate_new = String.format("%.6f", rate)
            binding.rate.text = "$$rate_new"
            crypto?.let {
                binding.titleName.text = it.name_full
                Glide.with(itemView.context)
                    .load(it.icon_url)
                    .placeholder(R.drawable.loading_gif) // Placeholder if the image is loading
                    .error(R.drawable.error_gif) // Placeholder if there is an error loading the image
                    .into(binding.imageCoin)
            }
        }
    }
}
