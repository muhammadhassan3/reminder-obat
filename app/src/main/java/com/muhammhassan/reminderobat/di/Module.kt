package com.muhammhassan.reminderobat.di

import com.muhammhassan.reminderobat.ui.view.add.drug.AddDrugViewModel
import com.muhammhassan.reminderobat.ui.view.add.schedule.AddReminderViewModel
import com.muhammhassan.reminderobat.ui.view.add.stock.AddStockViewModel
import com.muhammhassan.reminderobat.ui.view.alarm.ReminderDetailViewModel
import com.muhammhassan.reminderobat.ui.view.detail.history.DetailHistoryViewModel
import com.muhammhassan.reminderobat.ui.view.detail.schedule.DetailScheduleViewModel
import com.muhammhassan.reminderobat.ui.view.home.HomeViewModel
import com.muhammhassan.reminderobat.ui.view.progress.ProgressViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val viewModelModule = module {
        viewModel { HomeViewModel(get()) }
        viewModel { AddDrugViewModel() }
        viewModel { AddReminderViewModel() }
        viewModel { AddStockViewModel(get()) }
        viewModel { ReminderDetailViewModel(get()) }
        viewModel { ProgressViewModel(get()) }
        viewModel { DetailHistoryViewModel(get()) }
        viewModel { DetailScheduleViewModel(get()) }
    }
}