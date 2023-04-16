package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun addHistory(data: HistoryEntity)
    fun deleteHistory(data: HistoryEntity)
    fun getDetail(id: Long): Flow<HistoryEntity>

    fun getAll(): Flow<List<HistoryEntity>>
}