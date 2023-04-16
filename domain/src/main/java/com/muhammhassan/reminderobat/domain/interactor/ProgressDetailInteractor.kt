package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.HistoryRepository
import com.muhammhassan.reminderobat.domain.model.HistoryModel
import com.muhammhassan.reminderobat.domain.usecase.ProgressDetailUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProgressDetailInteractor(private val historyRepository: HistoryRepository): ProgressDetailUseCase {
    override fun getHistory(historyId: Long): Flow<HistoryModel> {
        return historyRepository.getDetail(historyId).map { it.mapToModel() }
    }
}