package com.memo

import com.login.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface MemoService {
    //@GET("timeline?sort%5B0%5D=updatedAt%2CDESC")
    @GET("timeline?sort%5B0%5D=id%2CDESC")
    fun getSection(
//        @Header("Authorization") authHeader: String?
    ): Call<ArrayList<Memo>>

    @POST("timeline")
    fun createUser(@Body memo: Memo?): Call<Memo>

}

