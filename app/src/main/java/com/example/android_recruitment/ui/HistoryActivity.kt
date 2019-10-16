package com.example.android_recruitment.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.android_recruitment.R
import com.example.android_recruitment.model.generic.Rate
import com.example.android_recruitment.model.generic.ResultVolley
import com.example.android_recruitment.model.response.CurrencyReponse
import com.example.android_recruitment.service.CurrencyService
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.toolbar
import kotlinx.android.synthetic.main.activity_main.*

class HistoryActivity : AppCompatActivity() {
    private val activity: AppCompatActivity = this
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)

        val fromCurrency = intent.getStringExtra("fromCurrency")
        val toCurrency = intent.getStringExtra("toCurrency")

        getCurrencyGistoryRequest(fromCurrency, toCurrency)
    }

    fun getCurrencyGistoryRequest(fromCurrency: String, toCurrency: String) {
        val observable = PublishSubject.create<ResultVolley>()

        val observer = object : Observer<ResultVolley> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(result: ResultVolley) {
                try {
                    if (result.sucess) {
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

}
