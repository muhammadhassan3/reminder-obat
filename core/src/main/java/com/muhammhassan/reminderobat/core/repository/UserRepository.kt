package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun register(name: String, email: String, password: String): Flow<ApiResponse<String>>

    fun getToken(): Flow<String?>
    suspend fun setToken(value: String)
    suspend fun deleteToken()
}