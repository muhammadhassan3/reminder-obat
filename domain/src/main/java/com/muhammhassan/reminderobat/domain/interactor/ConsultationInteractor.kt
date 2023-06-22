package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.ConsultationRepository
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.ConsultationUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConsultationInteractor(private val consultation: ConsultationRepository) : ConsultationUseCase {
    override fun sendMessage(message: String): Flow<UiState<String>> =
        consultation.sendMessage(message = message).map {
            it.mapToUi { message ->
                message
            }
        }
}