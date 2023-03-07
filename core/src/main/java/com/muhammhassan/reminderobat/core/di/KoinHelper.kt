package com.muhammhassan.reminderobat.core.di

import com.muhammhassan.reminderobat.core.repository.DrugRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper : KoinComponent {
    private val drugsRepository: DrugRepository by inject()

    fun getDrugsDetail(drugsId: Int) = drugsRepository.getDetail(id = drugsId.toLong())
}