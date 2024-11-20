package com.example.upstoxapp.domain.repository

import com.example.upstoxapp.data.ApiResult
import com.example.upstoxapp.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface UpstoxRepository {
    suspend fun getUserData() : Flow<ApiResult<UserData>>
}