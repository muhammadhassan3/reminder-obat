package com.muhammhassan.reminderobat.domain.usecase

interface LoginUseCase {
    suspend fun saveToken(value: String)
}