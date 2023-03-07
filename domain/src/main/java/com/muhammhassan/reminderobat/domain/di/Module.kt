package com.muhammhassan.reminderobat.domain.di

import com.muhammhassan.reminderobat.domain.interactor.AddStockInteractor
import com.muhammhassan.reminderobat.domain.interactor.ReminderDetailInteractor
import com.muhammhassan.reminderobat.domain.interactor.HomeInteractor
import com.muhammhassan.reminderobat.domain.usecase.AddStockUseCase
import com.muhammhassan.reminderobat.domain.usecase.ReminderDetailUseCase
import com.muhammhassan.reminderobat.domain.usecase.HomeUseCase
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        single<HomeUseCase> { HomeInteractor(get()) }
        single<AddStockUseCase> { AddStockInteractor(get(), get()) }
        single<ReminderDetailUseCase> { ReminderDetailInteractor(get(), get(), get()) }
    }
}