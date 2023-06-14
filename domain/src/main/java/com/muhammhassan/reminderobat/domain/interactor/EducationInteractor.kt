package com.muhammhassan.reminderobat.domain.interactor

import com.muhammhassan.reminderobat.core.repository.EducationRepository
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.EducationUseCase
import com.muhammhassan.reminderobat.domain.utils.Mapper.mapToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EducationInteractor(private val education: EducationRepository): EducationUseCase {
    override fun getData(): Flow<UiState<List<Articles>>> {
        return education.getEducation().map {
            it.mapToUi{list ->
                list.map { item -> Articles(
                    id = item.id,
                    title = item.title,
                    content = item.content,
                    image = item.imageUrl
                ) }
            }
        }
    }
}