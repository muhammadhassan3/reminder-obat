package com.muhammhassan.reminderobat.domain.usecase

interface AuthUseCase {
    suspend fun setToken(value: String)
}