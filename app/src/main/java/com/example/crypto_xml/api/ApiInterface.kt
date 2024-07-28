package com.example.crypto_xml.api

import com.example.crypto_xml.models.MarketModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel>
}
