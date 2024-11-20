package com.example.upstoxapp.data.api.service

import com.example.upstoxapp.data.model.UserData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getUserData(): Response<UserData>
}