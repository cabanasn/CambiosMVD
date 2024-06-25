package com.ircl.cambiosmvd.data.remote.response


import com.google.gson.annotations.SerializedName

data class Rate(
    @SerializedName("buy")
    val buy: Double,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("exchange_id")
    val exchangeId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("sell")
    val sell: Double
)

enum class Currency {
    USD, EUR, ARS, BRL, UYU
}