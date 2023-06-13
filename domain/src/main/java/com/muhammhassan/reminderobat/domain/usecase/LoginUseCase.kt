package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(email: String, password: String): Flow<UiState<String>>
    suspend fun saveToken(value: String)
}