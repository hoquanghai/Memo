package com.memo


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitMemo {
    private const val BASE_URL = "http://172.16.2.9:5012/api/v1/"
    //private const val BASE_URL = "http://172.16.0.210:5007/api/v1/"
    val instance: MemoService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(MemoService::class.java)
    }
}