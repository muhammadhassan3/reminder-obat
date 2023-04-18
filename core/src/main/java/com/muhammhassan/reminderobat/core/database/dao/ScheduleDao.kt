package com.muhammhassan.reminderobat.core.database.dao

import androidx.room.*
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.ScheduleAndDrug
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllSchedule(list: List<ScheduleEntity>)
    @Query("delete from schedule where id = :id")
    fun delete(id: Long)

    @Transaction
    @Query("select * from schedule where days like :day and time >= :time  order by time asc")
    fun getNearestSchedule(day: String, time: String): Flow<List<ScheduleAndDrug>>

    @Transaction
    @Query("select * from schedule where days = :day and time = :time")
    fun getListSchedule(day: Int, time: String): Flow<List<ScheduleAndDrug>>

    @Transaction
    @Query("select * from schedule where id = :id")
    fun getSchedule(id: Long): Flow<ScheduleAndDrug?>
}