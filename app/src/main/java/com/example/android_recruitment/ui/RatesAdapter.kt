package com.example.android_recruitment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_recruitment.R
import com.example.android_recruitment.model.generic.Rate
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_currency.view.*
import kotlinx.android.synthetic.main.row_rate.view.*
import java.math.BigDecimal
import java.math.RoundingMode

class RatesAdapter(val context: Context, val currency: String) : RecyclerView.Adapter<RatesAdapter.MyViewHolder>() {
    var rates: MutableList<Rate> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_rate, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rate = rates[position]
        holder.fromValue.text = "1,00"
        val urlFrom = "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${currency.toLowerCase()}.png"
        Glide.with(context).load(urlFrom).into(holder.fromFlag)

        val totalTo = BigDecimal(rate.value).setScale(2, RoundingMode.HALF_EVEN)
        holder.toValue.text = totalTo.toString().replace(".", ",")
        val urlTo = "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${rate.currency.toLowerCase()}.png"
        Glide.with(context).load(urlTo).into(holder.toFlag)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fromFlag = view.currency_from_flag
        val fromValue = view.currency_from_value
        val toFlag = view.currency_to_flag
        val toValue = view.currency_to_value
    }
}