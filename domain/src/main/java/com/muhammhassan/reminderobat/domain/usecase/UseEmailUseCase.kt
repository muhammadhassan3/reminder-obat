package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface UseEmailUseCase {
    fun getResetToken(email: String): Flow<UiState<ResetTokenModel>>
}