package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.domain.usecase.LoginUseCase

class LoginInteractor(private val user: UserRepository): LoginUseCase {

    override suspend fun saveToken(value: String) {
        user.setToken(value)
    }
}