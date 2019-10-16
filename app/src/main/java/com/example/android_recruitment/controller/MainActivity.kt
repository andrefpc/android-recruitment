package com.example.android_recruitment.controller

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_recruitment.R
import com.example.android_recruitment.adapter.RatesAdapter
import com.example.android_recruitment.model.generic.Rate
import com.example.android_recruitment.model.generic.ResultVolley
import com.example.android_recruitment.model.response.CountryInfoReponse
import com.example.android_recruitment.model.response.CurrencyReponse
import com.example.android_recruitment.service.CurrencyService
import com.example.android_recruitment.util.GenericUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private val activity: AppCompatActivity = this
    private val context: Context = this
    private var listCurrencies: List<String> = arrayListOf()
    private var rates: HashMap<String, Double> = HashMap()

    private var selectedFrom = "BRL"
    private var selectedTo = "USD"

    private var adapter: RatesAdapter = RatesAdapter(context)

    private var updating = false
    private var totalFrom = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        selectExchange("BRL", "USD")

        main_from_layout.setOnClickListener {
            val bottomSheetCurrencies = BottomSheetCurrencies(activity, listClick("from"))
            bottomSheetCurrencies.show(listCurrencies.sorted())
        }

        main_to_layout.setOnClickListener {
            val bottomSheetCurrencies = BottomSheetCurrencies(activity, listClick("to"))
            bottomSheetCurrencies.show(listCurrencies.sorted())
        }

        main_from_value.doAfterTextChanged {
            if (!updating) {
                updating = true
                it?.let {
                    GenericUtil().formatMoney(it, main_from_value)
                }
                updating = false

                updateToValue()
            }
        }

        main_change_button.setOnClickListener {
            val to = selectedTo
            val from = selectedFrom
            selectExchange(to, from)
        }

        main_from_value.requestFocus()
        main_from_value.setSelection(main_from_value.text!!.length)

        val layoutManager = LinearLayoutManager(activity.applicationContext)
        main_recycler_currencies.layoutManager = layoutManager
        main_recycler_currencies.adapter = adapter
    }

    private fun updateToValue() {
        var value = main_from_value.text.toString().replace(",", "")
        if (value.isNotEmpty()) {
            totalFrom = value.toInt()

            val rate = rates[selectedTo]
            rate?.let {
                val totalTo =
                    BigDecimal((totalFrom * rate) / 100).setScale(2, RoundingMode.HALF_EVEN)
                main_to_value.text = totalTo.toString().replace(".", ",")
            }
        }
    }

    private fun listClick(type: String): Subject<String> {
        val observable = PublishSubject.create<String>()

        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(currency: String) {
                if (type == "from") {
                    selectExchange(currency, selectedTo)
                } else {
                    selectExchange(selectedFrom, currency)
                }
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        }

        observable.subscribe(observer)
        return observable
    }

    private fun selectExchange(fromCurrency: String, toCurrency: String) {
        selectedFrom = fromCurrency
        val url =
            "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${fromCurrency.toLowerCase()}.png"
        Glide.with(context).load(url).into(main_from_flag)
        main_from_currecy.text = fromCurrency
        getCurrencyValuesRequest(fromCurrency, toCurrency)
        getCurrencyInfo(fromCurrency, "from")
    }

    private fun selectToCurrency(currency: String) {
        selectedTo = currency
        val url =
            "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${currency.toLowerCase()}.png"
        Glide.with(context).load(url).into(main_to_flag)
        main_to_currecy.text = currency
        getCurrencyInfo(currency, "to")

        updateToValue()
    }

    fun getCurrencyValuesRequest(fromCurrency: String, toCurrency: String) {
        val observable = PublishSubject.create<ResultVolley>()

        val observer = object : Observer<ResultVolley> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(result: ResultVolley) {
                try {
                    if (result.sucess) {
                        val currencyResponse =
                            Gson().fromJson(result.body, CurrencyReponse::class.java)
                        rates = currencyResponse.rates
                        adapter.currency = fromCurrency
                        adapter.rates.clear()

                        for (rate in rates.toSortedMap()) {
                            adapter.rates.add(Rate(rate.key, rate.value))
                        }
                        adapter.notifyDataSetChanged()

                        listCurrencies = currencyResponse.rates.keys.toList()

                        selectToCurrency(toCurrency)
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

        CurrencyService(context, main_loading).getCurrencyValues(fromCurrency, observable)
    }

    fun getCurrencyInfo(currency: String, type: String) {
        val observable = PublishSubject.create<ResultVolley>()

        val observer = object : Observer<ResultVolley> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(result: ResultVolley) {
                try {
                    if (result.sucess) {
                        val listType = object : TypeToken<List<CountryInfoReponse>>() {}.type
                        val currencyInfoResponse =
                            Gson().fromJson<List<CountryInfoReponse>>(result.body, listType)
                        if (type == "from") {
                            main_from_symbol.text = currencyInfoResponse[0].currencies[0].symbol
                        } else {
                            main_to_symbol.text = currencyInfoResponse[0].currencies[0].symbol
                        }
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

        CurrencyService(context, main_loading).getCurrencyInfo(currency, observable)
    }

    override fun onBackPressed() {
        closeAppAlert()
    }

    fun closeAppAlert() {
        var builder = Builder(this)
        builder.setMessage(getString(R.string.alert_close_title))

        var alertDialog = builder.create()

        builder.setPositiveButton(getString(R.string.alert_close_yes_button)) { _, _ ->
            alertDialog.cancel()
            finish()
        }

        builder.setNegativeButton(getString(R.string.alert_close_no_button)) { _, _ ->
            alertDialog.cancel()
        }
        builder.show()
    }
}
