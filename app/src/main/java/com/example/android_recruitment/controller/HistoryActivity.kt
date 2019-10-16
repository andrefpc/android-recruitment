package com.example.android_recruitment.controller

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_recruitment.R
import com.example.android_recruitment.adapter.HistoryAdapter
import com.example.android_recruitment.model.generic.History
import com.example.android_recruitment.model.generic.ResultVolley
import com.example.android_recruitment.model.response.CountryInfoReponse
import com.example.android_recruitment.model.response.CurrencyHistoryReponse
import com.example.android_recruitment.service.CurrencyService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.toolbar
import kotlinx.android.synthetic.main.content_history.*
import java.text.SimpleDateFormat


class HistoryActivity : AppCompatActivity() {
    private val activity: AppCompatActivity = this
    private val context: Context = this

    private var symbol = ""

    private var adapter: HistoryAdapter = HistoryAdapter(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fromCurrency = intent.getStringExtra("fromCurrency")
        val toCurrency = intent.getStringExtra("toCurrency")

        history_from_currency.text = fromCurrency
        val urlFromCurrency = "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${fromCurrency.toLowerCase()}.png"
        Glide.with(context).load(urlFromCurrency).into(history_from_flag)

        history_to_currency.text = toCurrency
        val urlToCurrency = "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${toCurrency.toLowerCase()}.png"
        Glide.with(context).load(urlToCurrency).into(history_to_flag)

        val layoutManager = LinearLayoutManager(activity.applicationContext)
        history_list.layoutManager = layoutManager
        history_list.adapter = adapter

        getCurrencyInfo(fromCurrency, toCurrency)

    }

    fun getCurrencyInfo(fromCurrency: String, toCurrency: String) {
        val observable = PublishSubject.create<ResultVolley>()

        val observer = object : Observer<ResultVolley> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(result: ResultVolley) {
                try {
                    if (result.sucess) {
                        val listType = object : TypeToken<List<CountryInfoReponse>>() {}.type
                        val currencyInfoResponse =
                            Gson().fromJson<List<CountryInfoReponse>>(result.body, listType)
                        symbol = currencyInfoResponse[0].currencies[0].symbol
                        adapter.symbol = symbol
                        getCurrencyGistoryRequest(fromCurrency, toCurrency)
                    } else {
                        Toast.makeText(context, result.body, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        }

        observable.subscribe(observer)

        CurrencyService(context, history_loading).getCurrencyInfo(toCurrency, observable)
    }

    fun getCurrencyGistoryRequest(fromCurrency: String, toCurrency: String) {
        val observable = PublishSubject.create<ResultVolley>()

        val observer = object : Observer<ResultVolley> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(result: ResultVolley) {
                try {
                    if (result.sucess) {
                        val currencyHistoryReponse =
                            Gson().fromJson(result.body, CurrencyHistoryReponse::class.java)

                        parseHistoryResponse(currencyHistoryReponse)

                    } else {
                        Toast.makeText(context, result.body, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        }

        observable.subscribe(observer)

        CurrencyService(context, history_loading).getCurrencyHistory(
            fromCurrency,
            toCurrency,
            observable
        )
    }

    private fun parseHistoryResponse(currencyHistoryReponse: CurrencyHistoryReponse) {
        val historyList = ArrayList<History>()
        for (rates in currencyHistoryReponse.rates) {
            for (value in rates.value) {
                val sdfFrom = SimpleDateFormat("yyyy-MM-dd")
                historyList.add(History(sdfFrom.parse(rates.key), value.value))
            }
        }

        historyList.sortWith(Comparator<History> { p1, p2 ->
            when {
                p1.date.after(p2.date) -> 1
                else -> -1
            }
        })

        adapter.historyList.clear()
        val historyMap = LinkedHashMap<String, Float>()
        for (history in historyList) {
            val sdfTo = SimpleDateFormat("dd/MM/yyyy")
            historyMap[sdfTo.format(history.date)] = history.value.toFloat()
            adapter.historyList.add(history)
        }
        adapter.notifyDataSetChanged()


        history_chart.gradientFillColors =
            intArrayOf(
                resources.getColor(R.color.light_gray),
                Color.TRANSPARENT
            )
        history_chart.animation.duration = 1000
        history_chart.animate(historyMap)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
