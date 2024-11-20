package com.example.upstoxapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upstoxapp.R
import com.example.upstoxapp.data.ApiResult
import com.example.upstoxapp.databinding.ActivityMainBinding
import com.example.upstoxapp.databinding.PortfolioDetailBsLayoutBinding
import com.example.upstoxapp.ui.adapter.StockAdapter
import com.example.upstoxapp.ui.viewModel.UserDataViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mViewModel: UserDataViewModel by viewModels()
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mBindingBS: PortfolioDetailBsLayoutBinding
    private lateinit var mAdapter: StockAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mViewModel.getUserData()

        collectData()
        initViews()
    }

    private fun initViews() {

        mBinding.holdingRV.layoutManager = LinearLayoutManager(this)
        mAdapter = StockAdapter(arrayListOf())
        mBinding.holdingRV.adapter = mAdapter

        val bottomSheetDialog = BottomSheetDialog(this)
        mBindingBS = PortfolioDetailBsLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(mBindingBS.root)

        mBinding.totalPnlView.setOnClickListener {
            initViewBS()
            bottomSheetDialog.show()
        }
    }

    private fun initViewBS() {

        mBindingBS.currentValueL.keyTV.text = getString(R.string.current_value)
        mBindingBS.currentValueL.valueTV.text = "₹ ${mViewModel.currentPortfolioValue()}"

        mBindingBS.totalInvestmentValueL.keyTV.text = getString(R.string.total_investments)
        mBindingBS.totalInvestmentValueL.valueTV.text =
            "₹ ${mViewModel.currentTotalInvestmentValue()}"

        mBindingBS.todayPnLValueL.keyTV.text = getString(R.string.today_s_profit_loss)
        mBindingBS.todayPnLValueL.valueTV.text = "₹ ${mViewModel.todayProfitAndLossValue()}"

        mBindingBS.profitAndLossValueL.keyTV.text = getString(R.string.profit_and_loss)
        mBindingBS.profitAndLossValueL.valueTV.text = "₹ ${mViewModel.getTotalPnl()}"
    }


    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.userData.collect {

                    when (it) {
                        is ApiResult.Error -> {
                            mBinding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.something_went_wrong),
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        is ApiResult.Loading -> {
                            mBinding.progressBar.visibility = View.VISIBLE
                        }

                        is ApiResult.Success -> {
                            mBinding.progressBar.visibility = View.GONE
                            mBinding.tvTotalInvestment.text = "₹ ${mViewModel.getTotalPnl()}"

                            mAdapter.addData(it.data?.userData?.holdingList ?: arrayListOf())
                        }
                    }
                }
            }
        }
    }
}