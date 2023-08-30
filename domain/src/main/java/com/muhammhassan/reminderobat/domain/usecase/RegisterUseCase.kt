package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Flow<UiState<String>>
}