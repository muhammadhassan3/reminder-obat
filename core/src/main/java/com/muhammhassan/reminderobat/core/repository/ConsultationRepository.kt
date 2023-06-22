package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.api.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ConsultationRepository {
    fun sendMessage(message: String): Flow<ApiResponse<String>>
}