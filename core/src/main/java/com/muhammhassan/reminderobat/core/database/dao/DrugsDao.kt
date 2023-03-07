package com.muhammhassan.reminderobat.core.database.dao

import androidx.room.*
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.relation.DrugsAndSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDrugs(data: DrugsEntity): Long

    @Update
    fun editDrugs(data: DrugsEntity)

    @Delete
    fun delete(data: DrugsEntity)

    @Query("select * from drugs")
    fun getAll(): Flow<List<DrugsEntity>>

    @Transaction
    @Query(value = "select * from drugs drug where drug.id = :drugId")
    fun getDetail(drugId: Long): Flow<DrugsAndSchedule>

    @Query("select * from drugs where id = :drugId")
    fun getDataSync(drugId: Long): DrugsEntity
}