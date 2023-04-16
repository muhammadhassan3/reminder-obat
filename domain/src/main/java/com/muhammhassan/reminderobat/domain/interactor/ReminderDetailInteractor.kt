package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.DrugRepository
import com.muhammhassan.reminderobat.core.repository.HistoryRepository
import com.muhammhassan.reminderobat.core.repository.ScheduleRepository
import com.muhammhassan.reminderobat.domain.model.DrugsModel
import com.muhammhassan.reminderobat.domain.usecase.ReminderDetailUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToHistoryModel
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderDetailInteractor(
    private val drugRepository: DrugRepository,
    private val scheduleRepository: ScheduleRepository,
    private val historyRepository: HistoryRepository
) : ReminderDetailUseCase {
    override fun reduceStock(drugsId: Long) {
        drugRepository.reduceStock(drugsId)
    }

    override fun getData(scheduleId: Long): Flow<DrugsModel> {
        return scheduleRepository.getSchedule(scheduleId).map { it.mapToListModel() }
    }

    override suspend fun addHistory(data: DrugsModel, status: String) {
        historyRepository.addHistory(data.mapToHistoryModel(status))
    }
}