package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.DrugRepository
import com.muhammhassan.reminderobat.core.repository.ScheduleRepository
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.domain.model.SavedDrugs
import com.muhammhassan.reminderobat.domain.usecase.AddStockUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapDrugsEntity
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapScheduleEntity
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToAlarmModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AddStockInteractor(
    private val drugsRepository: DrugRepository,
    private val scheduleRepository: ScheduleRepository
) : AddStockUseCase {
    override suspend fun addData(data: DrugsData): Flow<SavedDrugs> {
        val drugsEntity = data.mapDrugsEntity()

        val drugId = drugsRepository.addDrug(drugsEntity)
        val schedule = data.mapScheduleEntity(drugId)
        scheduleRepository.addAllSchedule(schedule)

        val drugsData = drugsRepository.getDetail(drugId)
        return drugsData.map { drugs ->
            SavedDrugs(drugId,
                drugs.schedule.map { it.mapToAlarmModel(drugs.drugs.drugsName) })
        }
    }
}