package com.muhammhassan.reminderobat.core.datasource

import com.muhammhassan.reminderobat.core.database.DrugsReminderDatabase
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class LocalDatasourceImpl(private val db: DrugsReminderDatabase) : LocalDatasource {
    private val executor = Executors.newSingleThreadExecutor()
    private val drugsDao = db.drugsDao()
    private val scheduleDao = db.scheduleDao()
    private val historyDao = db.historyDao()

    override fun getDrugs(): Flow<List<DrugsEntity>> {
        return drugsDao.getAll()
    }

    override fun addDrugs(data: DrugsEntity) {
        TODO("Not yet implemented")
    }

    override fun editDrugs(data: DrugsEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteDrugs(data: DrugsEntity) {
        TODO("Not yet implemented")
    }

    override fun getSchedule(drugId: Int): Flow<List<ScheduleEntity>> {
        TODO("Not yet implemented")
    }

    override fun addSchedule(data: ScheduleEntity) {
        TODO("Not yet implemented")
    }

    override fun editSchedule(data: ScheduleEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteSchedule(data: ScheduleEntity) {
        TODO("Not yet implemented")
    }

    override fun addHistory(data: HistoryEntity) {
        TODO("Not yet implemented")
    }

    override fun getDetail(drugId: Int): Flow<DrugsAndSchedule> {
        TODO("Not yet implemented")
    }
}