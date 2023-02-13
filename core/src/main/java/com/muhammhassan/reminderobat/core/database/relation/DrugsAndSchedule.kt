package com.muhammhassan.reminderobat.core.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity

data class DrugsAndSchedule(
    @Embedded
    val drugs: DrugsEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "drugId"
    )
    val schedule: List<ScheduleEntity>,
)
