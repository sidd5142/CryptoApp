package com.example.crypto_xml.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.crypto_xml.adapter.MarketAdapter
import com.example.crypto_xml.api.ApiInterface
import com.example.crypto_xml.api.ApiUtilities
import com.example.crypto_xml.databinding.FragmentTopLossGainBinding
import com.example.crypto_xml.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

class TopLossGainFragment : Fragment() {

    lateinit var binding: FragmentTopLossGainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopLossGainBinding.inflate(layoutInflater)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {
        val position = requireArguments().getInt("position")
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if (res.body() != null)
            {
                withContext(Dispatchers.Main){
                    val dataitem = res.body()!!.data.cryptoCurrencyList

                    Collections.sort(dataitem) { o1, o2 ->
                        o2.quotes[0].percentChange24h.compareTo(o1.quotes[0].percentChange24h)
                    }

                    binding.progressBar.visibility = GONE

                    val list = ArrayList<CryptoCurrency>()

                    if(position == 0)
                    {
                        list.clear()
                        for (i in 0..9) {
                            list.add(dataitem[i])
                        }

                        binding.topGainLoseRecyclerView.adapter= MarketAdapter(
                            requireContext(),
                            list,
                            "home"
                        )
                    }else {
                        list.clear()
                        for (i in 0..9) {
                            list.add(dataitem[dataitem.size-1-i])
                        }

                        binding.topGainLoseRecyclerView.adapter= MarketAdapter(
                            requireContext(),
                            list,
                            "home"
                        )
                    }

                }
            }
        }
    }
}