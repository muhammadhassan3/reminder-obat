package com.muhammhassan.reminderobat.domain.usecase

import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    suspend fun setToken(value: String)
}