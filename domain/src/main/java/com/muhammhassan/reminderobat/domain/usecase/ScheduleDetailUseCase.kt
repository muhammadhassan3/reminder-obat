package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.ScheduleModel
import kotlinx.coroutines.flow.Flow

interface ScheduleDetailUseCase {
    fun getDetail(scheduleId: Long): Flow<ScheduleModel>
    fun delete(scheduleId: Long)
}