package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.response.ResetTokenResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun register(name: String, email: String, password: String): Flow<ApiResponse<String>>

    fun getToken(): Flow<String?>
    suspend fun setToken(value: String)
    suspend fun deleteToken()


    //reset token
    fun getResetToken(email: String): Flow<ApiResponse<ResetTokenResponse>>
    fun verifyResetToken(verifyToken : String, otp: String): Flow<ApiResponse<ResetTokenResponse>>
    fun resendOTP(resendToken: String): Flow<ApiResponse<ResetTokenResponse>>
    fun resetPassword(verifyToken: String, password: String): Flow<ApiResponse<String>>
}