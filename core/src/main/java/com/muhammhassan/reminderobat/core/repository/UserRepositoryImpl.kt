package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.datasource.LocalDatasource
import com.muhammhassan.reminderobat.core.datasource.RemoteDatasource
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val remote: RemoteDatasource, private val local: LocalDatasource): UserRepository {

    override fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<String>> {
        return remote.register(name, email, password)
    }

    override fun getToken(): Flow<String?> {
        return local.getToken()
    }

    override suspend fun setToken(value: String) {
        local.setToken(value)
    }

    override suspend fun deleteToken() {
        local.deleteToken()
    }
}