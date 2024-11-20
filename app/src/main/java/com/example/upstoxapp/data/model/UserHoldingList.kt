package com.example.upstoxapp.data.model

import com.google.gson.annotations.SerializedName

class UserHoldingList(
    @SerializedName("userHolding")
    val holdingList: ArrayList<UserHoldingData>? = arrayListOf()
)