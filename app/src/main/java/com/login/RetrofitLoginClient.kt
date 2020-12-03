package com.login

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitLoginClient {
    private const val SERVER_FILE = "http://172.16.0.210"
    //private const val SERVER_FILE = "http://172.16.2.9"
    private const val ACCOUNT_PORT = "3999"
    private const val BASE_URL = "${SERVER_FILE}:${ACCOUNT_PORT}"
    val instance: UserApi by lazy {
        val retrofit = Retrofit.Builder()

            .baseUrl(RetrofitLoginClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(UserApi::class.java)

    }
}