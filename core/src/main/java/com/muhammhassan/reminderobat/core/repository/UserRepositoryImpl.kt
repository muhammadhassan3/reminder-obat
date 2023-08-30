package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.response.ResetTokenResponse
import com.muhammhassan.reminderobat.core.api.response.UserResponse
import com.muhammhassan.reminderobat.core.datasource.LocalDatasource
import com.muhammhassan.reminderobat.core.datasource.RemoteDatasource
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val remote: RemoteDatasource, private val local: LocalDatasource): UserRepository {

    override fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Flow<ApiResponse<String>> {
        return remote.register(name, email, password, phoneNumber)
    }

    override fun login(phoneNumber: String): Flow<ApiResponse<UserResponse>> {
        return remote.login(phoneNumber)
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

    override fun getResetToken(email: String): Flow<ApiResponse<ResetTokenResponse>> {
        return remote.getResetToken(email)
    }

    override fun verifyResetToken(
        verifyToken: String,
        otp: String
    ): Flow<ApiResponse<ResetTokenResponse>> {
        return remote.verifyResetToken(verifyToken, otp)
    }

    override fun resendOTP(resendToken: String): Flow<ApiResponse<ResetTokenResponse>> {
        return remote.resendOTP(resendToken)
    }

    override fun resetPassword(verifyToken: String, password: String): Flow<ApiResponse<String>> {
        return remote.resetPassword(verifyToken, password)
    }
}