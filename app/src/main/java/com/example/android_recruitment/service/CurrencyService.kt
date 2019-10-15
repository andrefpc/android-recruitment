package com.example.android_recruitment.service

import android.content.Context
import android.widget.LinearLayout
import com.example.android_recruitment.model.generic.ResultVolley
import io.reactivex.subjects.Subject

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
}