package com.muhammhassan.reminderobat.ui

import android.app.Application
import com.muhammhassan.reminderobat.ui.di.Module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(
                viewModelModule
            ))
        }
    }
}