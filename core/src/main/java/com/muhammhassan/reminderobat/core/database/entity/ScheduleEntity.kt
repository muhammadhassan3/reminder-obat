package com.muhammhassan.reminderobat.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val time: String,
    val drugId: Long,
    val days: String,
)