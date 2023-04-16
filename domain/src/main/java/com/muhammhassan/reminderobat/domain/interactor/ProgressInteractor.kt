package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.HistoryRepository
import com.muhammhassan.reminderobat.domain.model.HistoryListModel
import com.muhammhassan.reminderobat.domain.usecase.ProgressUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProgressInteractor(private val repository: HistoryRepository): ProgressUseCase {
    override fun getData(): Flow<List<HistoryListModel>> {
        return repository.getAll().map { it.map{data -> data.mapToListModel()} }
    }
}