package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.ScheduleRepository
import com.muhammhassan.reminderobat.domain.model.ScheduleModel
import com.muhammhassan.reminderobat.domain.usecase.ScheduleDetailUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduleDetailInteractor(private val scheduleRepository: ScheduleRepository): ScheduleDetailUseCase {
    override fun getDetail(scheduleId: Long): Flow<ScheduleModel?> {
        return scheduleRepository.getSchedule(scheduleId).map { it?.mapToModel() }
    }

    override fun delete(scheduleId: Long) {
        scheduleRepository.deleteSchedule(scheduleId)
    }
}