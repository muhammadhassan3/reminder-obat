package com.muhammhassan.reminderobat.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val drugName: String,
    val drugsWeight: String,
    val drugsType: String,
    val time: String,
    val afterEat: Int,
    val confirmTime: String,
    val condition: String,
    val stock: Int,
    val day: Int,
    val createdAt: String,
)
