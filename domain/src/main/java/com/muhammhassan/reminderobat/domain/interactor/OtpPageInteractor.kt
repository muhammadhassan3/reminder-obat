package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.OtpPageUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OtpPageInteractor(private val user: UserRepository): OtpPageUseCase {
    override fun verifyToken(verifyToken: String, otp: String): Flow<UiState<String>> {
        return user.verifyResetToken(verifyToken, otp).map { response ->
            response.mapToUi {
                it.token
            }
        }
    }

    override fun resendToken(refreshToken: String): Flow<UiState<ResetTokenModel>> {
        return user.resendOTP(refreshToken).map {
            it.mapToUi { token ->
                ResetTokenModel(token.token, token.resendToken, token.expire)
            }
        }
    }
}