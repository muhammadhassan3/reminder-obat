package com.muhammhassan.reminderobat.core.database.entity

import androidx.room.Entity

@Entity(tableName = "alarm_history")
data class HistoryEntity(
    val id: Int,
    val drugName: String,
    val drugsWeight: String,
    val drugsType: String,
    val time: Long,
    val afterEat: Int,
    val confirmTime: Long,
    val condition: String,
    val stock: Int
)
