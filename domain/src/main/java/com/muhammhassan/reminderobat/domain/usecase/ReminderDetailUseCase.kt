package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.DrugsModel
import kotlinx.coroutines.flow.Flow

interface ReminderDetailUseCase {
    fun reduceStock(drugsId: Long)
    fun getData(scheduleId: Long): Flow<DrugsModel>

    suspend fun addHistory(data: DrugsModel)
}