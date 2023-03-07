package com.muhammhassan.reminderobat.domain.usecase

import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.domain.model.SavedDrugs
import kotlinx.coroutines.flow.Flow

interface AddStockUseCase {
    suspend fun addData(data: DrugsData): Flow<SavedDrugs>
}