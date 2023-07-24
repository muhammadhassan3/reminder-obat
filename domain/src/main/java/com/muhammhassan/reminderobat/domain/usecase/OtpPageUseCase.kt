package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface OtpPageUseCase {
    fun verifyToken(verifyToken: String, otp: String): Flow<UiState<String>>
    fun resendToken(refreshToken: String): Flow<UiState<ResetTokenModel>>
}