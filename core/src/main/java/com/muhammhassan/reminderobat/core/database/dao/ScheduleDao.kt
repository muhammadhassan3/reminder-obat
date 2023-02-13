package com.muhammhassan.reminderobat.core.database.dao

import androidx.room.*
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDrugs(data: ScheduleEntity)

    @Update
    fun editDrugs(data: ScheduleEntity)

    @Delete
    fun delete(data: ScheduleEntity)

    @Query("select * from schedule")
    fun getAll(): Flow<List<ScheduleEntity>>
}