package com.login

import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("/api/v1/user/login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<LoginResponse>

    @POST("/api/v1/user/login")
    fun login(
        @Body body: Map<String, String>
    ): Call<LoginResponse>

    @GET("/api/v1/user/{userId}")
    fun getUser(
        @Path("userId") userId: String,
        @Header("Authorization") authHeader: String?
    ): Call<UserResponse>


    class LoginResponse(
        val result: String,
        val success: Boolean,
        val message: String
    )

    class UserResponse(
        val result: User,
        val success: Boolean,
        val message: String
    )
}