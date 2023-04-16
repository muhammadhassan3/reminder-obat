package com.muhammhassan.reminderobat.domain.di

import com.muhammhassan.reminderobat.domain.interactor.*
import com.muhammhassan.reminderobat.domain.usecase.*
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        single<HomeUseCase> { HomeInteractor(get()) }
        single<AddStockUseCase> { AddStockInteractor(get(), get()) }
        single<ReminderDetailUseCase> { ReminderDetailInteractor(get(), get(), get()) }
        single<ProgressUseCase> { ProgressInteractor(get()) }
        single<ProgressDetailUseCase> { ProgressDetailInteractor(get()) }
        single<ScheduleDetailUseCase> { ScheduleDetailInteractor(get()) }
    }
}