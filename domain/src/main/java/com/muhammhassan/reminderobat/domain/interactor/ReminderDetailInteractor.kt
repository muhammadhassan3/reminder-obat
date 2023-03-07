package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.repository.DrugRepository
import com.muhammhassan.reminderobat.core.repository.HistoryRepository
import com.muhammhassan.reminderobat.core.repository.ScheduleRepository
import com.muhammhassan.reminderobat.core.utils.Utils
import com.muhammhassan.reminderobat.domain.model.DrugsModel
import com.muhammhassan.reminderobat.domain.usecase.ReminderDetailUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class ReminderDetailInteractor(
    private val drugRepository: DrugRepository,
    private val scheduleRepository: ScheduleRepository,
    private val historyRepository: HistoryRepository
) : ReminderDetailUseCase {
    override fun reduceStock(drugsId: Long) {
        drugRepository.reduceStock(drugsId)
    }

    override fun getData(scheduleId: Long): Flow<DrugsModel> {
        return scheduleRepository.getSchedule(scheduleId).map { it.mapToModel() }
    }

    override suspend fun addHistory(data: DrugsModel) {
        val calendar = Calendar.getInstance()
        val history = HistoryEntity(
            id = null,
            afterEat = data.afterEat,
            time = data.time,
            drugsType = data.type,
            drugsWeight = data.weight,
            day = calendar[Calendar.DAY_OF_WEEK],
            stock = data.stock,
            condition = data.condition,
            confirmTime = Utils.parseDateToString(calendar.time),
            createdAt = Utils.parseDateToISO(calendar.time),
            drugName = data.title
        )

        historyRepository.addHistory(history)
    }
}