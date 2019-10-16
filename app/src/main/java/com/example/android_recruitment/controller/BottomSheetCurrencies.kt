package com.example.android_recruitment.controller

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_recruitment.R
import com.example.android_recruitment.adapters.CurrencyAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.bottom_sheet_currencies.view.*

class BottomSheetCurrencies(private val activity: AppCompatActivity, val clickObserver: Subject<String>) {

    var mBottomSheetDialog: BottomSheetDialog = BottomSheetDialog(activity)

    fun show(currencies: List<String>) {
        try {
            val sheetView = activity.layoutInflater.inflate(R.layout.bottom_sheet_currencies, null)

            var adapter = CurrencyAdapter(
                activity.applicationContext,
                listClick()
            )

            val layoutManager = LinearLayoutManager(activity.applicationContext)
            sheetView.bottom_sheet_currency_list.layoutManager = layoutManager
            sheetView.bottom_sheet_currency_list.adapter = adapter
            adapter.currencies.addAll(currencies)
            adapter.notifyItemRangeInserted(0, currencies.size)

            mBottomSheetDialog.setContentView(sheetView)
            mBottomSheetDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun listClick(): Subject<String> {
        val observable = PublishSubject.create<String>()

        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(currency: String) {
                mBottomSheetDialog.cancel()
                clickObserver.onNext(currency)
            }

            override fun onError(e: Throwable) {}

            override fun onComplete()  {}
        }

        observable.subscribe(observer)
        return observable
    }
}