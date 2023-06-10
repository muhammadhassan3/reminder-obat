package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.datasource.RemoteDatasource
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val remote: RemoteDatasource): UserRepository {
    override fun login(email: String, password: String): Flow<ApiResponse<String>> {
        return remote.login(email, password)
    }

    override fun regiter(name: String, email: String, password: String): Flow<ApiResponse<String>> {
        return remote.register(name, email, password)
    }
}