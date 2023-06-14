package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.RegisterUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RegisterInteractor(private val repository: UserRepository): RegisterUseCase {
    override fun register(
        name: String,
        email: String,
        password: String
    ): Flow<UiState<String>> {
        return repository.register(name, email, password).map{
            it.mapToUi{ message ->
                message
            }
        }
    }
}