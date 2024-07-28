package com.example.crypto_xml.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crypto_xml.R
import com.example.crypto_xml.databinding.TopCurrencyLayoutBinding
import com.example.crypto_xml.models.CryptoCurrency

class TopMarketAdapter(private val context: Context, private val list: List<CryptoCurrency>) : RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    inner class TopMarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TopCurrencyLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.top_currency_layout, parent, false)
        return TopMarketViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position]

        holder.binding.topCurrencyNameTextView.text = item.name

        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.topCurrencyImageView)

        val changeTextColor = if (item.quotes[0].percentChange24h > 0) {
            context.resources.getColor(R.color.green)
        } else {
            context.resources.getColor(R.color.red)
        }
        holder.binding.topCurrencyChangeTextView.setTextColor(changeTextColor)

        val changeText = if (item.quotes[0].percentChange24h > 0) {
            "+ ${String.format("%.2f", item.quotes[0].percentChange24h)} %"
        } else {
            "${String.format("%.2f", item.quotes[0].percentChange24h)} %"
        }
        holder.binding.topCurrencyChangeTextView.text = changeText
    }
}
