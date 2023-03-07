package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.database.DrugsReminderDatabase
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import com.muhammhassan.reminderobat.core.database.relation.ScheduleAndDrug
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.concurrent.Executors

class LocalDatasourceImpl(db: DrugsReminderDatabase) : LocalDatasource {
    private val executor = Executors.newSingleThreadExecutor()
    private val drugsDao = db.drugsDao()
    private val scheduleDao = db.scheduleDao()
    private val historyDao = db.historyDao()

    override fun getAllDrugs(): Flow<List<DrugsEntity>> {
        return drugsDao.getAll()
    }

    override suspend fun addDrugs(data: DrugsEntity): Long {
        return drugsDao.addDrugs(data)
    }

    override fun reduceStock(drugsId: Long) {
        val drug = drugsDao.getDataSync(drugsId)
        if (drug.stock > 0) {
            drug.stock = drug.stock - 1
        } else Timber.i("Stock empty")
        drugsDao.editDrugs(drug)
    }

    override fun editDrugs(data: DrugsEntity) {
        executor.execute {
            drugsDao.editDrugs(data)
        }
    }

    override fun deleteDrugs(data: DrugsEntity) {
        executor.execute {
            drugsDao.delete(data)
        }
    }

    override fun getSpecificSchedule(day: Int, time: String): Flow<List<ScheduleAndDrug>> {
        return scheduleDao.getListSchedule(day, time)
    }

    override fun getSchedule(id: Long): Flow<ScheduleAndDrug> {
        return scheduleDao.getSchedule(id)
    }

    override fun getNearestSchedule(
        day: Int, time: String, date: String
    ): Flow<List<ScheduleAndDrug>> {
        return scheduleDao.getNearestSchedule("%$day%", time).map { data ->
            data.filter { item ->
                item.drugs.startDate <= date && item.drugs.endDate >= date
            }
        }
    }

    override suspend fun addAllSchedule(data: List<ScheduleEntity>) {
        executor.execute {
            scheduleDao.addAllSchedule(data)
        }
    }

    override fun deleteSchedule(data: ScheduleEntity) {
        executor.execute {
            scheduleDao.delete(data)
        }
    }

    override fun addHistory(data: HistoryEntity) {
        executor.execute {
            historyDao.addHistory(data)
        }
    }

    override fun deleteHistory(data: HistoryEntity) {
        executor.execute {
            historyDao.delete(data)
        }
    }

    override fun getAllHistory(): Flow<List<HistoryEntity>> {
        return historyDao.getAll()
    }

    override fun getDetail(drugId: Long): Flow<DrugsAndSchedule> {
        return drugsDao.getDetail(drugId)
    }
}