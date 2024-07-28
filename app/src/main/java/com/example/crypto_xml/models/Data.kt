package com.example.crypto_xml.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cryptoCurrencyList")
    val cryptoCurrencyList: List<CryptoCurrency>,
    @SerializedName("totalCount")
    val totalCount: String
)