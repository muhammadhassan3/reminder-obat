package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.api.response.Article
import com.muhammhassan.reminderobat.core.api.response.ResetTokenResponse
import com.muhammhassan.reminderobat.core.api.response.UserResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDatasource {
    fun register(name: String, email: String, password: String, phoneNumber: String): Flow<ApiResponse<String>>
    fun login(phoneNumber: String): Flow<ApiResponse<UserResponse>>
    fun getData(): Flow<ApiResponse<List<Article>>>

    fun sendMessage(message: String): Flow<ApiResponse<String>>

    //reset token
    fun getResetToken(email: String): Flow<ApiResponse<ResetTokenResponse>>
    fun verifyResetToken(verifyToken : String, otp: String): Flow<ApiResponse<ResetTokenResponse>>
    fun resendOTP(resendToken: String): Flow<ApiResponse<ResetTokenResponse>>
    fun resetPassword(verifyToken: String, password: String): Flow<ApiResponse<String>>
}