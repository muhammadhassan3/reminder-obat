package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface EducationUseCase {
    fun getData(): Flow<UiState<List<Articles>>>
}