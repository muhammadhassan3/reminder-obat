package com.muhammhassan.reminderobat.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammhassan.reminderobat.core.database.dao.DrugsDao
import com.muhammhassan.reminderobat.core.database.dao.HistoryDao
import com.muhammhassan.reminderobat.core.database.dao.ScheduleDao
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity

@Database(entities = [DrugsEntity::class, ScheduleEntity::class, HistoryEntity::class], version = 1, exportSchema = false)
abstract class DrugsReminderDatabase: RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun drugsDao(): DrugsDao
    abstract fun historyDao(): HistoryDao
}