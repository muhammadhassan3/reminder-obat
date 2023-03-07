package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.datasource.LocalDatasource
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(private val local: LocalDatasource): HistoryRepository {
    override fun addHistory(data: HistoryEntity) {
        local.addHistory(data)
    }

    override fun deleteHistory(data: HistoryEntity) {
        local.deleteHistory(data)
    }

    override fun getAll(): Flow<List<HistoryEntity>> {
        return local.getAllHistory()
    }
}