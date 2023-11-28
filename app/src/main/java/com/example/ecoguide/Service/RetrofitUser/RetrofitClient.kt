package com.example.ecoguide.Service.RetrofitUser

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.21:3000/"
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    fun <T>buildService (serviceType: Class<T>):T{
        return retrofit.create(serviceType)
    }
}