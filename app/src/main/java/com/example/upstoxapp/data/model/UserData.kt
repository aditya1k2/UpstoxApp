package com.example.upstoxapp.data.model

import com.google.gson.annotations.SerializedName


data class UserData(
    @SerializedName("data")
    val userData: UserHoldingList? = null
)
