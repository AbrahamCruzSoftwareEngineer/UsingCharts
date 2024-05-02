package com.example.usingcharts.data.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiResponse {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.random.org/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

