package com.muhammhassan.reminderobat.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drugs")
data class DrugsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val drugsName: String,
    val drugsWeight: String,
    val afterEat: Int,
    val drugsType: String,
    val startDate: Int,
    val endDate: Int,
    val condition: String,
    val stock: Int,
)