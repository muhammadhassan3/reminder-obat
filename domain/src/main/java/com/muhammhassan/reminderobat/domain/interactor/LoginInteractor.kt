package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.LoginUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginInteractor(private val user: UserRepository): LoginUseCase {
    override fun login(email: String, password: String): Flow<UiState<String>> {
        return user.login(email, password).map {
            it.mapToUi()
        }
    }

    override suspend fun saveToken(value: String) {
        user.setToken(value)
    }
}