package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    suspend fun saveToken(value: String)

    suspend fun login(phoneNumber: String): Flow<UiState<String>>
}