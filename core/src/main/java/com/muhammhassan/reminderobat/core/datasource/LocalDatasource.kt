package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import com.muhammhassan.reminderobat.core.database.relation.ScheduleAndDrug
import kotlinx.coroutines.flow.Flow

interface LocalDatasource {
    fun getAllDrugs(): Flow<List<DrugsEntity>>
    suspend fun addDrugs(data: DrugsEntity): Long

    fun reduceStock(drugsId: Long)
    fun editDrugs(data: DrugsEntity)
    fun deleteDrugs(data: DrugsEntity)

    fun getSpecificSchedule(day: Int, time: String): Flow<List<ScheduleAndDrug>>

    fun getSchedule(id: Long): Flow<ScheduleAndDrug?>
    fun getNearestSchedule(day: Int, time: String, date: String): Flow<List<ScheduleAndDrug>>
    suspend fun addAllSchedule(data: List<ScheduleEntity>)
    fun deleteSchedule(id: Long)

    fun addHistory(data: HistoryEntity)
    fun deleteHistory(data: HistoryEntity)
    fun getAllHistory(): Flow<List<HistoryEntity>>

    fun getDetail(drugId: Long): Flow<DrugsAndSchedule>
    fun getDetailHistory(id: Long): Flow<HistoryEntity>

    suspend fun setToken(value: String)
    fun getToken(): Flow<String?>
    suspend fun deleteToken()
}