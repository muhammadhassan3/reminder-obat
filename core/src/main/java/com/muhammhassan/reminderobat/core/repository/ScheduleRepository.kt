package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.ScheduleAndDrug
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun addAllSchedule(data: List<ScheduleEntity>)
    fun deleteSchedule(data: ScheduleEntity)
    fun getNearestAlarm(day: Int, time: String = "00:00", date: String): Flow<List<ScheduleAndDrug>>
    fun getScheduleList(day: Int, time: String): Flow<List<ScheduleAndDrug>>
    fun getSchedule(id: Long): Flow<ScheduleAndDrug>
}