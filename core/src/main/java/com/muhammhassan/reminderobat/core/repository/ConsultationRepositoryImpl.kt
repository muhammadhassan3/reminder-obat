package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.datasource.RemoteDatasource
import kotlinx.coroutines.flow.Flow

class ConsultationRepositoryImpl(private val remote: RemoteDatasource): ConsultationRepository {
    override fun sendMessage(message: String): Flow<ApiResponse<String>> = remote.sendMessage(message)
}