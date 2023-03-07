package com.muhammhassan.reminderobat.domain.utils

import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.ScheduleAndDrug
import com.muhammhassan.reminderobat.core.service.alarm.AlarmModel
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateToString
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateWithoutDayName
import com.muhammhassan.reminderobat.core.utils.Utils.parseStringToDate
import com.muhammhassan.reminderobat.domain.model.DrugItem
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.domain.model.DrugsModel

object Mapper {
    fun ScheduleAndDrug.mapToItem() = DrugItem(
        title = this.drugs.drugsName,
        type = this.drugs.drugsType,
        time = this.schedule.time
    )

    fun DrugsData.mapDrugsEntity() = DrugsEntity(
        id = null,
        drugsName = name!!,
        drugsWeight = weight!!,
        afterEat = afterEat,
        drugsType = type!!,
        startDate = parseDateToString(startDate!!),
        endDate = parseDateToString(endDate!!),
        condition = condition!!,
        stock = stock
    )

    fun DrugsData.mapScheduleEntity(drugId: Long): List<ScheduleEntity> {
        val days = day!!.joinToString()
        val data = hour?.map { ScheduleEntity(id = null, days = days, time = it, drugId = drugId) }
        return data ?: listOf()
    }

    fun ScheduleEntity.mapToAlarmModel(drugsName: String): AlarmModel =
        AlarmModel(id?.toInt() ?: 0, time, drugsName, days)

    fun ScheduleAndDrug.mapToModel() = DrugsModel(
        id = drugs.id!!,
        title = drugs.drugsName,
        time = schedule.time,
        weight = drugs.drugsWeight,
        type = drugs.drugsType,
        afterEat = drugs.afterEat,
        startDate = parseDateWithoutDayName(
            parseStringToDate(drugs.startDate)
        ),
        endDate = parseDateWithoutDayName(
            parseStringToDate(drugs.endDate)
        ),
        condition = drugs.condition,
        stock = drugs.stock,
        scheduleId = schedule.id!!
    )
}