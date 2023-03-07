package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.ScheduleRepository
import com.muhammhassan.reminderobat.domain.model.DrugItem
import com.muhammhassan.reminderobat.domain.usecase.HomeUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeInteractor(private val scheduleRepos: ScheduleRepository) : HomeUseCase {
    override fun getData(day: Int, time: String, date: String): Flow<List<DrugItem>> {
        return scheduleRepos.getNearestAlarm(day = day, time = time, date).map {  list ->
            list.map { it.mapToItem() }
        }
    }
}