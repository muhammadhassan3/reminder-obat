package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.UseEmailUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class UseEmailInteractor(private val user: UserRepository): UseEmailUseCase {
    override fun getResetToken(email: String): Flow<UiState<ResetTokenModel>> {
        return user.getResetToken(email).map {
            it.mapToUi { token->
                Timber.e(token.toString())
                ResetTokenModel(token.token, token.resendToken, token.expire)
            }
        }
    }
}