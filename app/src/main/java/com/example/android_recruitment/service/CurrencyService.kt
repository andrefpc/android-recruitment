package com.example.android_recruitment.service

import android.content.Context
import android.widget.LinearLayout
import com.example.android_recruitment.model.generic.ResultVolley
import io.reactivex.subjects.Subject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CurrencyService(val context: Context, val loading: LinearLayout) {
    fun getCurrencyValues(currency: String, observable: Subject<ResultVolley>?) {
        val url = "https://api.exchangeratesapi.io/latest?base=$currency"
        val header = HashMap<String, String>()
        header["Content-Type"] = "application/json; charset=utf-8"
        val volleyFactory = VolleyFactory(context, observable, header, loading, null)
        volleyFactory.get(url)
    }

    fun getCurrencyInfo(currency: String, observable: Subject<ResultVolley>?) {
        val url = "https://restcountries.eu/rest/v2/currency/$currency"
        val header = HashMap<String, String>()
        header["Content-Type"] = "application/json; charset=utf-8"
        val volleyFactory = VolleyFactory(context, observable, header, loading, null)
        volleyFactory.get(url)
    }

    fun getCurrencyHistory(fromCurrency: String, toCurrency: String, observable: Subject<ResultVolley>?) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val lastMonth = calendar.time
        val today = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd")

        val url = "https://api.exchangeratesapi.io/history?start_at=${sdf.format(lastMonth)}&end_at=${sdf.format(today)}&base=$fromCurrency&symbols=$toCurrency"
        val header = HashMap<String, String>()
        header["Content-Type"] = "application/json; charset=utf-8"
        val volleyFactory = VolleyFactory(context, observable, header, loading, null)
        volleyFactory.get(url)
    }
}