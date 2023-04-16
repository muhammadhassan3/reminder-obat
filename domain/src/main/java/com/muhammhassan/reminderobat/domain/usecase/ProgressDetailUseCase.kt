package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.HistoryModel
import kotlinx.coroutines.flow.Flow

interface ProgressDetailUseCase {
    fun getHistory(historyId: Long): Flow<HistoryModel>
}