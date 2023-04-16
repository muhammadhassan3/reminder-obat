package com.muhammhassan.reminderobat.core.database.dao

import androidx.room.*
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHistory(data: HistoryEntity): Long

    @Delete
    fun delete(data: HistoryEntity)

    @Query("select * from alarm_history")
    fun getAll(): Flow<List<HistoryEntity>>

    @Query("select * from alarm_history where id = :id")
    fun getDetail(id: Long): Flow<HistoryEntity>
}