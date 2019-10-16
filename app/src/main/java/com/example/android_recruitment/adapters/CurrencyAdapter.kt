package com.example.android_recruitment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_recruitment.R
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.row_currency.view.*

class CurrencyAdapter(val context: Context, val clickObserver: Subject<String>) : RecyclerView.Adapter<CurrencyAdapter.MyViewHolder>() {
    var currencies: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_currency, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.name.text = currency
        val url = "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${currency.toLowerCase()}.png"
        Glide.with(context).load(url).into(holder.flag)

        holder.layout.setOnClickListener {
            clickObserver.onNext(currency)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.currency_layout
        val name = view.currency_name
        val flag = view.currency_flag
    }
}