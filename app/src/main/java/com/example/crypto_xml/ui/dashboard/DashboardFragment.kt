package com.example.crypto_xml.ui.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.crypto_xml.adapter.MarketAdapter
import com.example.crypto_xml.api.ApiInterface
import com.example.crypto_xml.api.ApiUtilities
import com.example.crypto_xml.databinding.FragmentDashboardBinding
import com.example.crypto_xml.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    private lateinit var list: List<CryptoCurrency>
    private lateinit var adapter: MarketAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(layoutInflater)

        list = listOf()

        adapter = MarketAdapter(requireContext(), list, "market")
        binding.currencyRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if (res.body() != null){

                withContext(Dispatchers.Main){
                    list = res.body()!!.data.cryptoCurrencyList

                    adapter.updateData(list)
                    binding.progressBar.visibility = GONE
                }
            }
        }

        searchCoin()

        return binding.root
    }

    lateinit var searchText : String
    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                searchText = p0.toString().toLowerCase()

                updateRecyclerView()
            }

        })
    }

    private fun updateRecyclerView() {
        val data = ArrayList<CryptoCurrency>()

        for(item in list){
            val coinName = item.name.lowercase(Locale.getDefault())
            val coinSymbol = item.symbol.lowercase(Locale.getDefault())

            if (coinName.contains(searchText) || coinSymbol.contains(searchText)){
                data.add(item)
            }

            adapter.updateData(data)
        }
    }
}