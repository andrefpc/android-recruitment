package com.example.android_recruitment.service

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android_recruitment.model.generic.ResultVolley
import com.example.android_recruitment.util.GenericUtil
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import io.reactivex.subjects.Subject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class VolleyFactory(
    private val context: Context,
    private val observable: Subject<ResultVolley>?,
    private val headerRequest: HashMap<String, String>,
    private val loading: LinearLayout,
    private val button: MaterialButton?
) {
    var bodyRequest: String = ""

    private var bodyResponse: String = ""
    private var code: Int = 0

    fun post(url: String) {
        printRequest(url, "POST")
        requestWithBody(url, Request.Method.POST)
    }

    fun put(url: String) {
        printRequest(url, "PUT")
        requestWithBody(url, Request.Method.PUT)
    }

    fun patch(url: String) {
        printRequest(url, "PATCH")
        requestWithBody(url, Request.Method.PATCH)
    }

    fun delete(url: String) {
        printRequest(url, "DELETE")
        requestWithoutBody(url, Request.Method.DELETE)
    }

    fun get(url: String) {
        printRequest(url, "GET")
        requestWithoutBody(url, Request.Method.GET)
    }

    private fun requestWithBody(url: String, method: Int) {
        showPb()

        val stringRequest = object : StringRequest(method, url, Response.Listener { response ->
            successResponse(response)
        }, Response.ErrorListener { error ->
            errorResponse(error)
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): HashMap<String, String> {
                return headerRequest
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                if (response != null) {
                    parseHeaderResponse(response)
                    code = response.statusCode
                }
                return super.parseNetworkResponse(response)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }


            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return bodyRequest.toByteArray()
            }
        }

        val defaultRetryPolicy =
            DefaultRetryPolicy(32000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = defaultRetryPolicy
        val request = Volley.newRequestQueue(context)
        request.add(stringRequest)
    }

    private fun requestWithoutBody(url: String, method: Int) {
        showPb()

        val stringRequest = object : StringRequest(method, url, Response.Listener { response ->
            successResponse(response)
        }, Response.ErrorListener { error ->
            errorResponse(error)
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): HashMap<String, String> {
                return headerRequest
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                if (response != null) {
                    parseHeaderResponse(response)
                    code = response.statusCode
                }
                return super.parseNetworkResponse(response)
            }
        }

        val defaultRetryPolicy =
            DefaultRetryPolicy(32000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = defaultRetryPolicy
        val request = Volley.newRequestQueue(context)
        request.add(stringRequest)
    }


    private fun successResponse(response: String) {
        hidePb()

        bodyResponse = response;
        val resultVolley =
            ResultVolley(true, bodyResponse, code)
        printSuccessResponse()
        observable?.onNext(resultVolley)
    }

    private fun errorResponse(error: VolleyError) {
        hidePb()
        bodyResponse = parseBodyResponse(error.networkResponse)
        if (error.networkResponse != null) {
            code = error.networkResponse.statusCode
        }
        val resultVolley =
            ResultVolley(false, bodyResponse, code)
        resultVolley.error = error
        observable?.onNext(resultVolley)
        printErrorResponse()
    }

    private fun showPb() {
        loading?.let {
            it.visibility = View.VISIBLE
            button?.let {
                button.visibility = View.GONE
                val parentWidth = (loading.getParent() as View).measuredWidth
                val widthAnimator =
                    ValueAnimator.ofInt(parentWidth, GenericUtil().dpToPX(48, context))
                widthAnimator.duration = 100
                widthAnimator.interpolator = DecelerateInterpolator()
                widthAnimator.addUpdateListener { animation ->
                    loading.getLayoutParams().width = animation.animatedValue as Int
                    loading.requestLayout()
                }
                widthAnimator.start()
            }
        }
    }

    private fun hidePb() {
        loading?.let {
            it.visibility = View.GONE
            button?.let {
                it.visibility = View.VISIBLE
            }
        }
    }

    private fun parseBodyResponse(networkResponse: NetworkResponse?): String {
        var body: String = ""
        if (networkResponse?.data != null) {
            try {
                body = String(networkResponse.data, Charset.forName("UTF-8"))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        return body
    }

    private fun parseHeaderResponse(networkResponse: NetworkResponse?) {
        if (networkResponse?.data != null) {
            var headers: List<Header> = networkResponse.allHeaders

            for (header in headers) {
                //GET HEADER PARAMS
            }

        }
    }

    private fun printRequest(url: String, method: String) {
        val headerString = Gson().toJson(headerRequest)
        Log.d("APP_REQUEST", "METHOD: $method")
        Log.d("APP_REQUEST", "URL: $url")
        Log.d("APP_REQUEST", "HEADER (REQUEST): $headerString")
        if (bodyRequest != null) {
            Log.d("APP_REQUEST", "BODY (REQUEST): $bodyRequest")
        }
    }

    private fun printSuccessResponse() {
        Log.d("APP_REQUEST", "BODY (RESPONSE): $bodyResponse")
    }

    private fun printErrorResponse() {
        Log.d("APP_REQUEST", "STATUS CODE (ERROR): $code")
        Log.d("APP_REQUEST", "BODY (ERROR): $bodyResponse")
    }
}