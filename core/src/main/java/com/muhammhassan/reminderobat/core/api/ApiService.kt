package com.muhammhassan.reminderobat.core.api

import com.muhammhassan.reminderobat.core.api.response.BaseResponse
import com.muhammhassan.reminderobat.core.api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface ApiService {
    @FormUrlEncoded
    @GET("/register")
    suspend fun register(@Field("name") name: String,@Field("email") email: String,@Field("password") password: String): Response<BaseResponse<Nothing>>

    @GET("/login")
    @FormUrlEncoded
    suspend fun login(@Field("email") email: String,@Field("password") password: String): Response<BaseResponse<LoginResponse>>
}