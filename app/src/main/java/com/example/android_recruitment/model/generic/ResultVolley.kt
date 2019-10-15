package com.example.android_recruitment.model.generic

import com.android.volley.VolleyError

class ResultVolley(var sucess: Boolean, var body: String, var code: Int) {
    var error: VolleyError? = null
}