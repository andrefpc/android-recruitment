package com.example.android_recruitment.model.response
data class CurrencyHistoryReponse(
    val rates: HashMap<String, HashMap<String, Double>>,
    val base: String,
    val start_at: String,
    val end_at: String
)
