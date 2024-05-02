package com.example.usingcharts.data.api

import retrofit2.http.GET


interface ApiService {
    @GET("integers/?num=1&min=1&max=100&col=1&base=10&format=plain&rnd=new")
    suspend fun getRandomNumber(): String
}