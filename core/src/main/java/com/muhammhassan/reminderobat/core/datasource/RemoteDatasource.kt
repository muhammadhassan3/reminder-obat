package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.api.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDatasource {
    fun login(email: String, password: String): Flow<ApiResponse<String>>
    fun register(name: String, email: String, password: String): Flow<ApiResponse<String>>
}