package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.ApiService
import com.muhammhassan.reminderobat.core.api.response.Article
import com.muhammhassan.reminderobat.core.utils.Utils.parseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteDatasourceImpl(private val api: ApiService) : RemoteDatasource {

    override fun register(
        name: String, email: String, password: String
    ): Flow<ApiResponse<String>> = flow {
        emit(ApiResponse.Loading)
        val response = api.register(name, email, password)
        val body = response.body()
        if (response.isSuccessful) {
            body?.message?.let {
                emit(ApiResponse.Success(it))
            }
        } else {
            emit(ApiResponse.Error(response.parseError()))
        }
    }.catch {
        it.printStackTrace()
        emit(ApiResponse.Error("Jaringan yang kamu gunakan bermasalah, silahkan coba lagi dalam beberapa saat.."))
    }

    override fun getData(): Flow<ApiResponse<List<Article>>> = flow {
        emit(ApiResponse.Loading)
        val response = api.getEducation()
        val body = response.body()
        if(response.isSuccessful){
            body?.data?.let {
                emit(ApiResponse.Success(it))
            }
        }else{
            emit(ApiResponse.Error(response.parseError()))
        }
    }.catch {
        it.printStackTrace()
        emit(ApiResponse.Error("Jaringan yang kamu gunakan bermasalah, silahkan coba lagi dalam beberapa saat.."))
    }
}