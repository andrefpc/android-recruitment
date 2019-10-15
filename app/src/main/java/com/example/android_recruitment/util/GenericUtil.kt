package com.example.android_recruitment.util

import android.content.Context
import android.text.Editable
import androidx.appcompat.widget.AppCompatEditText
import java.math.RoundingMode
import java.text.DecimalFormat

class GenericUtil {
    fun dpToPX(dp: Int, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun formatMoney(it: Editable, editText: AppCompatEditText) {
        var selection = editText.selectionStart
        var str = it.toString()
        str = str.replace(",", "")
        try {
            var value = str.toInt()
            var doubleValue = value / 100.00
            val df = DecimalFormat("0.00")
            df.roundingMode = RoundingMode.DOWN
            val valueString = df.format(doubleValue).replace(".", ",")
            editText.setText(valueString)
            if (selection > valueString.length) {
                editText.setSelection(valueString.length)
            } else {
                editText.setSelection(selection)
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }
}