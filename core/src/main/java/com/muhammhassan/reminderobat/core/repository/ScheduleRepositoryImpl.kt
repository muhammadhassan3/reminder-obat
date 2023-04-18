package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.ScheduleAndDrug
import com.muhammhassan.reminderobat.core.datasource.LocalDatasource
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(private val local: LocalDatasource) : ScheduleRepository {
    override suspend fun addAllSchedule(data: List<ScheduleEntity>) {
        local.addAllSchedule(data)
    }

    override fun deleteSchedule(id: Long) {
        local.deleteSchedule(id)
    }

    override fun getNearestAlarm(
        day: Int, time: String, date: String
    ): Flow<List<ScheduleAndDrug>> {
        return local.getNearestSchedule(day, time, date)
    }

    override fun getScheduleList(day: Int, time: String): Flow<List<ScheduleAndDrug>> {
        return local.getSpecificSchedule(day, time)
    }

    override fun getSchedule(id: Long): Flow<ScheduleAndDrug?> {
        return local.getSchedule(id)
    }
}