package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import kotlinx.coroutines.flow.Flow

interface DrugRepository {
    suspend fun addDrug(data: DrugsEntity): Long
    fun editDrug(data: DrugsEntity)
    fun reduceStock(drugsId: Long)
    fun deleteDrug(data:DrugsEntity)
    fun getAll(): Flow<List<DrugsEntity>>
    fun getDetail(id: Long): Flow<DrugsAndSchedule>
}