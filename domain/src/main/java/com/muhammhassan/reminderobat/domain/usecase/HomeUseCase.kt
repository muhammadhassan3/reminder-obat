package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.DrugItem
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getData(day: Int, time: String, date: String): Flow<List<DrugItem>>
}