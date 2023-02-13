package com.muhammhassan.reminderobat.core.database.dao

import androidx.room.*
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDrugs(data: DrugsEntity)

    @Update
    fun editDrugs(data: DrugsEntity)

    @Delete
    fun delete(data: DrugsEntity)

    @Query("select * from drugs")
    fun getAll(): Flow<List<DrugsEntity>>

    @Transaction
    @Query("select * from drugs drug where drug.id = :drugId")
    fun getDetail(drugId: Int): Flow<DrugsAndSchedule>
}