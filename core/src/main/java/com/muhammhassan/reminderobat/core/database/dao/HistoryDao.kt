package com.muhammhassan.reminderobat.core.database.dao

import androidx.room.*
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDrugs(data: HistoryEntity)

    @Update
    fun editDrugs(data: HistoryEntity)

    @Delete
    fun delete(data: HistoryEntity)

}