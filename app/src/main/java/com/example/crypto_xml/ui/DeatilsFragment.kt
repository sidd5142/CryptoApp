package com.example.crypto_xml.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.crypto_xml.R
import com.example.crypto_xml.databinding.FragmentDeatilsBinding
import com.example.crypto_xml.models.CryptoCurrency
import androidx.appcompat.widget.AppCompatButton


class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDeatilsBinding

    private val args: DetailsFragmentArgs by navArgs()  // Corrected the declaration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDeatilsBinding.inflate(layoutInflater)

        val data: CryptoCurrency = args.data!!  // Use 'args' instead of 'item'

        setUpDetails(data)
        loadChart(data)
        setButtonOnClick(data)

        return binding.root
    }

    private fun setButtonOnClick(data: CryptoCurrency) {
        val oneMonth = binding.button
        val oneWeek = binding.button1
        val oneDay = binding.button2
        val fourHour = binding.button3
        val oneHour = binding.button4
        val fifteenMinute = binding.button5

        val clickListener = View.OnClickListener {
            when (it.id) {
                fifteenMinute.id -> loadChartData(it, "15", data, oneDay, oneMonth, oneWeek, fourHour, oneHour)
                oneHour.id -> loadChartData(it, "1H", data, oneDay, oneMonth, oneWeek, fourHour, fifteenMinute)
                oneMonth.id -> loadChartData(it, "M", data, oneDay, fifteenMinute, oneWeek, fourHour, oneHour)
                oneDay.id -> loadChartData(it, "D", data, fifteenMinute, oneMonth, oneWeek, fourHour, oneHour)
                fourHour.id -> loadChartData(it, "4H", data, oneDay, oneMonth, oneWeek, fifteenMinute, oneHour)
                oneWeek.id -> loadChartData(it, "W", data, oneDay, oneMonth, fifteenMinute, fourHour, oneHour)
            }
        }

        fifteenMinute.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        oneMonth.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
    }

    private fun loadChartData(
        it: View?,
        s: String,
        data: CryptoCurrency,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {
        disableButton(oneDay, oneMonth, oneWeek, fourHour, oneHour)
        it!!.setBackgroundResource(R.drawable.active_button)

        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + data.symbol
                .toString() + "USD&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=" +
                    "F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=" +
                    "[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    private fun disableButton(vararg buttons: AppCompatButton) {
        buttons.forEach { button ->
            button.setBackgroundResource(0)
        }
    }

    private fun loadChart(data: CryptoCurrency) {
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + data.symbol
                .toString() + "USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=" +
                    "F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=" +
                    "[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    private fun setUpDetails(data: CryptoCurrency) {
        binding.detailSymbolTextView.text = data.symbol

        Glide.with(requireContext())
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png")
            .thumbnail(Glide.with(requireContext()).load(R.drawable.spinner))
            .into(binding.detailImageView)

        binding.detailPriceTextView.text =
            "${String.format("$%.4f", data.quotes[0].price)}"

        if (data.quotes[0].percentChange24h > 0) {
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.green))
            binding.detailChangeImageView.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_up)
            binding.detailChangeTextView.text = "+ ${String.format("%.02f", data.quotes[0].percentChange24h)} %"
        } else {
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.red))
            binding.detailChangeImageView.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_down)
            binding.detailChangeTextView.text = "${String.format("%.02f", data.quotes[0].percentChange24h)} %"
        }
    }
}
