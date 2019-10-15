package com.example.android_recruitment.model.response
data class CurrencyReponse(
    val rates: HashMap<String, Double>,
    val base: String,
    val date: String
)
