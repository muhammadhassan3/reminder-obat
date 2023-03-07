package com.muhammhassan.reminderobat.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drugs")
data class DrugsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val drugsName: String,
    val drugsWeight: String,
    val afterEat: Int,
    val drugsType: String,
    val startDate: String,
    val endDate: String,
    val condition: String,
    var stock: Int,
)