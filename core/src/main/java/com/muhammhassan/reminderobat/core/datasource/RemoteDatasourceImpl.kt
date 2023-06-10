package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteDatasourceImpl(private val api: ApiService): RemoteDatasource {
    override fun login(email: String, password: String): Flow<ApiResponse<String>> = flow{
        emit(ApiResponse.Loading)
        val response = api.login(email, password)
        if(response.isSuccessful){
            response.body()?.let {
                emit(ApiResponse.Success(it.data.token))
            }
        }else{
            response.body()?.message?.let {
                emit(ApiResponse.Error(it))
            }
        }
    }.catch {
        emit(ApiResponse.Error("Jaringan yang kamu gunakan bermasalah, silahkan coba lagi dalam beberapa saat.."))
    }

    override fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<String>> = flow {
        emit(ApiResponse.Loading)
        val response = api.register(name, email, password)
        val body = response.body()
        if(response.isSuccessful){
            body?.message?.let {
                emit(ApiResponse.Success(it))
            }
        }else{
            body?.message?.let {
                emit(ApiResponse.Error(it))
            }
        }
    }.catch {
        emit(ApiResponse.Error("Jaringan yang kamu gunakan bermasalah, silahkan coba lagi dalam beberapa saat.."))
    }
}