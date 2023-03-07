package com.muhammhassan.reminderobat.core.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity

class ScheduleAndDrug(
    @Relation(
        parentColumn = "drugId",
        entityColumn = "id"
    )
    val drugs: DrugsEntity,
    @Embedded
    val schedule: ScheduleEntity
)