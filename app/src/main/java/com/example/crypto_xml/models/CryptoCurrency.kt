package com.example.crypto_xml.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CryptoCurrency(
    @SerializedName("auditInfoList")
    val auditInfoList: List<AuditInfo>,
    @SerializedName("badges")
    val badges: List<Int>,
    @SerializedName("circulatingSupply")
    val circulatingSupply: Double,
    @SerializedName("cmcRank")
    val cmcRank: Int,
    @SerializedName("dateAdded")
    val dateAdded: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isActive")
    val isActive: Double,
    @SerializedName("isAudited")
    val isAudited: Boolean,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("marketPairCount")
    val marketPairCount: Double,
    @SerializedName("maxSupply")
    val maxSupply: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("platform")
    val platform: Platform,
    @SerializedName("quotes")
    val quotes: List<Quote>,
    @SerializedName("selfReportedCirculatingSupply")
    val selfReportedCirculatingSupply: Double,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("totalSupply")
    val totalSupply: Double
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CryptoCurrency) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (symbol != other.symbol) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + symbol.hashCode()
        return result
    }
}
