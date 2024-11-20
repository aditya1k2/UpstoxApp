package com.example.upstoxapp.data.repoImpl

import com.example.upstoxapp.data.ApiResult
import com.example.upstoxapp.data.NetworkUtils
import com.example.upstoxapp.data.api.service.ApiService
import com.example.upstoxapp.data.model.UserData
import com.example.upstoxapp.domain.repository.UpstoxRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpstoxRepositoryImpl @Inject constructor(private val apiService: ApiService) : UpstoxRepository {

    override suspend fun getUserData(): Flow<ApiResult<UserData>> {
        return NetworkUtils.toResultFlow {
            apiService.getUserData()
        }
    }
}