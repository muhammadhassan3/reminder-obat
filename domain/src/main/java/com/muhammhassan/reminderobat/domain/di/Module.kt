package com.muhammhassan.reminderobat.domain.di

import com.muhammhassan.reminderobat.domain.interactor.AddStockInteractor
import com.muhammhassan.reminderobat.domain.interactor.HomeInteractor
import com.muhammhassan.reminderobat.domain.interactor.ProgressDetailInteractor
import com.muhammhassan.reminderobat.domain.interactor.ProgressInteractor
import com.muhammhassan.reminderobat.domain.interactor.RegisterInteractor
import com.muhammhassan.reminderobat.domain.interactor.ReminderDetailInteractor
import com.muhammhassan.reminderobat.domain.interactor.ScheduleDetailInteractor
import com.muhammhassan.reminderobat.domain.usecase.AddStockUseCase
import com.muhammhassan.reminderobat.domain.usecase.HomeUseCase
import com.muhammhassan.reminderobat.domain.usecase.ProgressDetailUseCase
import com.muhammhassan.reminderobat.domain.usecase.ProgressUseCase
import com.muhammhassan.reminderobat.domain.usecase.RegisterUseCase
import com.muhammhassan.reminderobat.domain.usecase.ReminderDetailUseCase
import com.muhammhassan.reminderobat.domain.usecase.ScheduleDetailUseCase
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        single<HomeUseCase> { HomeInteractor(get()) }
        single<AddStockUseCase> { AddStockInteractor(get(), get()) }
        single<ReminderDetailUseCase> { ReminderDetailInteractor(get(), get(), get()) }
        single<ProgressUseCase> { ProgressInteractor(get()) }
        single<ProgressDetailUseCase> { ProgressDetailInteractor(get()) }
        single<ScheduleDetailUseCase> { ScheduleDetailInteractor(get()) }
        single<RegisterUseCase> { RegisterInteractor(get()) }
    }
}