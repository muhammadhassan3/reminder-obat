package com.muhammhassan.reminderobat.core.repository

import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import com.muhammhassan.reminderobat.core.datasource.LocalDatasource
import kotlinx.coroutines.flow.Flow

class DrugRepositoryImpl(private var local: LocalDatasource): DrugRepository {
    override suspend fun addDrug(data: DrugsEntity): Long {
        return local.addDrugs(data)
    }

    override fun editDrug(data: DrugsEntity) {
        local.editDrugs(data)
    }

    override fun reduceStock(drugsId: Long) {
        local.reduceStock(drugsId)
    }

    override fun deleteDrug(data: DrugsEntity) {
        local.deleteDrugs(data)
    }

    override fun getAll(): Flow<List<DrugsEntity>> {
        return local.getAllDrugs()
    }

    override fun getDetail(id: Long): Flow<DrugsAndSchedule> {
        return local.getDetail(id)
    }
}