package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.HistoryListModel
import kotlinx.coroutines.flow.Flow

interface ProgressUseCase {
    fun getData(): Flow<List<HistoryListModel>>
}