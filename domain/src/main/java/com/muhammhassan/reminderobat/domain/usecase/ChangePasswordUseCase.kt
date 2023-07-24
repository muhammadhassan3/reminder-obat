package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface ChangePasswordUseCase {
    fun changePassword(token: String, password: String): Flow<UiState<String>>
}