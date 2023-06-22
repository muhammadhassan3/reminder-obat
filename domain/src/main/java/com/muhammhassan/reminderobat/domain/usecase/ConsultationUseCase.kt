package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface ConsultationUseCase {
    fun sendMessage(message: String): Flow<UiState<String>>
}