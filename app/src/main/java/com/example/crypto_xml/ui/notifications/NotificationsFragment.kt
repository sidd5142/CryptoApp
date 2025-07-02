package com.example.crypto_xml.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.crypto_xml.adapter.MarketAdapter
import com.example.crypto_xml.api.ApiInterface
import com.example.crypto_xml.api.ApiUtilities
import com.example.crypto_xml.databinding.FragmentNotificationsBinding
import com.example.crypto_xml.models.CryptoCurrency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var watchList: ArrayList<String>
    private lateinit var watchListItem: ArrayList<CryptoCurrency>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(layoutInflater)

        setupClearWatchlistButton()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        readData()
        fetchWatchlistData()
    }

    private fun setupClearWatchlistButton() {
        binding.clearWatchlistButton.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            watchList.clear()
            watchListItem.clear()

            binding.watchlistRecyclerView.adapter?.notifyDataSetChanged()
            binding.emptyTextView.visibility = View.VISIBLE
            binding.emptyTextView.text = "Watchlist is empty"
        }
    }

    private fun readData() {
        val sharedPreferences = requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("watchlist", ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }

    private fun fetchWatchlistData() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val res = ApiUtilities.getInstance()
                    .create(ApiInterface::class.java)
                    .getMarketData()

                if (res.body() != null) {
                    withContext(Dispatchers.Main) {
                        watchListItem = ArrayList()
                        watchListItem.clear()

                        for (watchData in watchList) {
                            for (item in res.body()!!.data.cryptoCurrencyList) {
                                if (watchData == item.symbol) {
                                    watchListItem.add(item)
                                }
                            }
                        }

                        binding.progressBar.visibility = View.GONE

                        if (watchListItem.isEmpty()) {
                            binding.emptyTextView.visibility = View.VISIBLE
                        } else {
                            binding.emptyTextView.visibility = View.GONE
                            binding.watchlistRecyclerView.adapter = MarketAdapter(
                                requireContext(),
                                watchListItem,
                                "watchlist"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.emptyTextView.text = "Error loading watchlist"
                }
            }
        }
    }
}

