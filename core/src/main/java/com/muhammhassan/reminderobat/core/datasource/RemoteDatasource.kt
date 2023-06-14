package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.response.Article
import kotlinx.coroutines.flow.Flow

interface RemoteDatasource {
    fun register(name: String, email: String, password: String): Flow<ApiResponse<String>>
    fun getData(): Flow<ApiResponse<List<Article>>>
}