package com.muhammhassan.reminderobat.core.api

import com.muhammhassan.reminderobat.core.api.response.Article
import com.muhammhassan.reminderobat.core.api.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(@Field("name") name: String,@Field("email") email: String,@Field("password") password: String): Response<BaseResponse<Nothing>>

    @GET("education")
    suspend fun getEducation(): Response<BaseResponse<List<Article>>>
    @FormUrlEncoded
    @POST("/chat")
    suspend fun sendMessage(@Field("message")message: String): Response<BaseResponse<String>>
}