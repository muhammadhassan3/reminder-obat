package com.muhammhassan.reminderobat.domain.utils

import com.muhammhassan.reminderobat.core.api.ApiResponse
import com.muhammhassan.reminderobat.core.database.entity.DrugsEntity
import com.muhammhassan.reminderobat.core.database.entity.HistoryEntity
import com.muhammhassan.reminderobat.core.database.entity.ScheduleEntity
import com.muhammhassan.reminderobat.core.database.relation.ScheduleAndDrug
import com.muhammhassan.reminderobat.core.service.model.AlarmModel
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateFromISO
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateToISO
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateToString
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateWithTime
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateWithoutDayName
import com.muhammhassan.reminderobat.core.utils.Utils.parseStringToDate
import com.muhammhassan.reminderobat.domain.model.DrugItem
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.domain.model.DrugsModel
import com.muhammhassan.reminderobat.domain.model.HistoryListModel
import com.muhammhassan.reminderobat.domain.model.HistoryModel
import com.muhammhassan.reminderobat.domain.model.ScheduleModel
import com.muhammhassan.reminderobat.domain.model.UiState
import java.util.Calendar

object Mapper {
    fun ScheduleAndDrug.mapToItem() = DrugItem(
        title = this.drugs.drugsName,
        type = this.drugs.drugsType,
        time = this.schedule.time,
        id = schedule.id!!
    )

    fun DrugsData.mapDrugsEntity() = DrugsEntity(
        id = null,
        drugsName = name!!.trim(),
        drugsWeight = weight!!.trim(),
        afterEat = afterEat,
        drugsType = type!!.trim(),
        startDate = parseDateToString(startDate!!),
        endDate = parseDateToString(endDate!!),
        condition = condition!!.trim(),
        stock = stock
    )

    fun DrugsData.mapScheduleEntity(drugId: Long): List<ScheduleEntity> {
        val days = day!!.joinToString()
        val data = hour?.map { ScheduleEntity(id = null, days = days, time = it, drugId = drugId) }
        return data ?: listOf()
    }

    fun ScheduleEntity.mapToAlarmModel(drugsName: String): AlarmModel =
        AlarmModel(id?.toInt() ?: 0, time, drugsName, days)

    fun ScheduleAndDrug.mapToListModel() = DrugsModel(
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

    fun HistoryEntity.mapToListModel() = HistoryListModel(
        id = this.id!!,
        drugName = this.drugName,
        createdAt = parseDateWithoutDayName(parseDateFromISO(this.createdAt)),
        status = this.status
    )

    fun HistoryEntity.mapToModel() = HistoryModel(
        drugName,
        drugsWeight,
        drugsType,
        time,
        afterEat,
        parseDateWithTime(parseDateFromISO(createdAt)),
        condition,
        stock,
        day,
        status
    )

    fun ScheduleAndDrug.mapToModel() = ScheduleModel(
        name = drugs.drugsName,
        condition = drugs.condition,
        time = schedule.time,
        afterEat = drugs.afterEat,
        weight = drugs.drugsWeight,
        type = drugs.drugsType,
    )

    fun DrugsModel.mapToHistoryModel(status: String): HistoryEntity{
        val calendar = Calendar.getInstance()
        return HistoryEntity(
            id = null,
            afterEat = afterEat,
            time = time,
            drugsType = type,
            drugsWeight = weight,
            day = calendar[Calendar.DAY_OF_WEEK],
            stock = stock,
            condition = condition,
            confirmTime = parseDateToString(calendar.time),
            createdAt = parseDateToISO(calendar.time),
            drugName = title,
            status = status
        )
    }

    fun <T> ApiResponse<T>.mapToUi(): UiState<T>{
        return when(this){
            is ApiResponse.Error -> UiState.Error(message)
            ApiResponse.Loading -> UiState.Loading
            is ApiResponse.Success -> UiState.Success(data)
        }
    }
}