package com.muhammhassan.reminderobat.ui.di

import com.muhammhassan.reminderobat.ui.view.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val viewModelModule = module {
        viewModel { HomeViewModel() }
    }
}