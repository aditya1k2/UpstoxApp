package com.example.upstoxapp.data.model


import com.google.gson.annotations.SerializedName

data class UserHoldingData(
    @SerializedName("avgPrice")
    val avgPrice: Double? = null,
    @SerializedName("close")
    val close: Double? = null,
    @SerializedName("ltp")
    val ltp: Double? = null,
    @SerializedName("quantity")
    val quantity: Double? = null,
    @SerializedName("symbol")
    val symbol: String? = null
)