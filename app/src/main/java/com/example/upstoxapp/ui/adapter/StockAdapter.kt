package com.example.upstoxapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upstoxapp.R
import com.example.upstoxapp.data.model.UserHoldingData
import com.example.upstoxapp.databinding.ItemStockParentBinding

class StockAdapter(
    private val stocks: ArrayList<UserHoldingData>
) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view =
            ItemStockParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stocks[position]
        holder.bind(stock)
    }

    override fun getItemCount(): Int = stocks.size

    class StockViewHolder(val binding: ItemStockParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: UserHoldingData) {
            //Bind parent data
            binding.tvStockName.text = stock.symbol
            binding.tvLTP.text = "₹ ${stock.ltp.toString()}"
            binding.tvNetQty.text = "NET QTY: ${stock.quantity}"
            val quantity = stock.quantity ?: 0.0
            val ltp = stock.ltp ?: 0.0
            val avgPrice = stock.avgPrice ?: 0.0
            val pnl = ((quantity * ltp) - (quantity * avgPrice)).let {
                String.format("%.2f", it).toDouble()
            }
            binding.tvPnL.text = "P&L: ${pnl}"
        }
    }

    fun addData(stocks: ArrayList<UserHoldingData>) {
        this.stocks.addAll(stocks)
        notifyDataSetChanged()
    }
}
