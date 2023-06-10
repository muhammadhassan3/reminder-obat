package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(email: String, password: String): Flow<ApiResponse<String>>
    fun regiter(name: String, email: String, password: String): Flow<ApiResponse<String>>
}