package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.ChangePasswordUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChangePasswordInteractor(private val user: UserRepository) : ChangePasswordUseCase {
    override fun changePassword(token: String, password: String): Flow<UiState<String>> {
        return user.resetPassword(token, password).map {
            it.mapToUi { result -> result }
        }
    }
}