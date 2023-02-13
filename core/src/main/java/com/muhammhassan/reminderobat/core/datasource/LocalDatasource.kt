package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import kotlinx.coroutines.flow.Flow

interface LocalDatasource {
    fun getDrugs(): Flow<List<DrugsEntity>>
    fun addDrugs(data: DrugsEntity)
    fun editDrugs(data: DrugsEntity)
    fun deleteDrugs(data: DrugsEntity)

    fun getSchedule(drugId: Int): Flow<List<ScheduleEntity>>
    fun addSchedule(data: ScheduleEntity)
    fun editSchedule(data: ScheduleEntity)
    fun deleteSchedule(data: ScheduleEntity)

    fun addHistory(data: HistoryEntity)

    fun getDetail(drugId: Int): Flow<DrugsAndSchedule>
}