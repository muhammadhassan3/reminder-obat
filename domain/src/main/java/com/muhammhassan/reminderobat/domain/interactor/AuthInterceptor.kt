package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.domain.usecase.AuthUseCase

class AuthInterceptor(private val user: UserRepository) : AuthUseCase {
    override suspend fun setToken(value: String) {
        user.setToken(value)
    }
}