package com.example.crypto_xml.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.crypto_xml.adapter.TopLossGainAdapter
import com.example.crypto_xml.adapter.TopMarketAdapter
import com.example.crypto_xml.api.ApiInterface
import com.example.crypto_xml.api.ApiUtilities
import com.example.crypto_xml.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        getTopCurrencyList()

        setTabLayout()

        return binding.root
    }

    private fun setTabLayout() {
        val adapter = TopLossGainAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                } else {
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.contentViewPager) {
            tab, position ->
            var title = if (position == 0){
                "Top Gainers"
            } else {
                "Top Losers"
            }
            tab.text = title
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService = ApiUtilities.getInstance().create(ApiInterface::class.java)
                val response = apiService.getMarketData()

                if (response.isSuccessful) {
                    val marketData = response.body()?.data?.cryptoCurrencyList
                    if (marketData != null) {
                        withContext(Dispatchers.Main) {
                            binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(requireContext(), marketData)
                        }
                    } else {
                        Log.e("Tag", "Market Data is null")
                    }
                } else {
                    Log.e("Tag", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("Tag", "Exception: ${e.message}")
            }
        }
    }
}
