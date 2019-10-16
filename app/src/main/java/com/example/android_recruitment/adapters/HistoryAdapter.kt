package com.example.android_recruitment.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_recruitment.R
import com.example.android_recruitment.model.generic.History
import com.example.android_recruitment.model.generic.Rate
import com.example.android_recruitment.ui.HistoryActivity
import kotlinx.android.synthetic.main.row_history.view.*
import kotlinx.android.synthetic.main.row_rate.view.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat

class HistoryAdapter(val context: Context) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    var historyList: MutableList<History> = mutableListOf()
    var symbol: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_history, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = historyList[position]
        val sdfTo = SimpleDateFormat("dd/MM/yyyy")
        holder.date.text = sdfTo.format(history.date)
        holder.value.text = "$symbol ${history.value.toString().replace(".", ",")}"

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date = view.history_date
        val value = view.history_value
    }
}