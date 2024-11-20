package com.example.upstoxapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upstoxapp.data.ApiResult
import com.example.upstoxapp.data.model.UserData
import com.example.upstoxapp.domain.repository.UpstoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(private val upstoxRepository: UpstoxRepository) :
    ViewModel() {

    private val _userData: MutableStateFlow<ApiResult<UserData>> = MutableStateFlow(ApiResult.Loading)
    val userData = _userData.asStateFlow()


    fun getUserData() {
        viewModelScope.launch {
            upstoxRepository.getUserData().collect {
                _userData.value = it
            }
        }
    }

    fun currentPortfolioValue(): Double {
        val a = _userData.value as ApiResult.Success
        var totalCurrentValue = 0.0
        a.data?.userData?.holdingList?.forEach { data ->
            totalCurrentValue += data.ltp?.let { data.quantity?.times(it) } ?: 0.0
        }

        return totalCurrentValue.let { String.format("%.2f", it).toDouble() }
    }

    fun currentTotalInvestmentValue(): Double {
        val a = _userData.value as ApiResult.Success
        var totalInvestmentValue = 0.0
        a.data?.userData?.holdingList?.forEach { data ->
            totalInvestmentValue += data.avgPrice?.let { data.quantity?.times(it) } ?: 0.0
        }

        return totalInvestmentValue.let { String.format("%.2f", it).toDouble() }
    }

    fun todayProfitAndLossValue(): Double {
        val a = _userData.value as ApiResult.Success
        var todaysProfitAndLossValue = 0.0
        a.data?.userData?.holdingList?.forEach { data ->
            val quantity = data.quantity ?: 0.0
            val closePrice = data.close ?: 0.0
            val ltp = data.ltp ?: 0.0
            todaysProfitAndLossValue += (quantity * (closePrice - ltp))
        }

        return todaysProfitAndLossValue.let { String.format("%.2f", it).toDouble() }
    }

    fun getTotalPnl(): Double {
        val totalPnL = (currentPortfolioValue() - currentTotalInvestmentValue())
        return totalPnL.let { String.format("%.2f", it).toDouble() }
    }
}