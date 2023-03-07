package com.muhammhassan.reminderobat

import android.app.Application
import com.muhammhassan.reminderobat.core.di.Module.databaseModule
import com.muhammhassan.reminderobat.core.di.Module.datasourceModule
import com.muhammhassan.reminderobat.core.di.Module.repositoryModule
import com.muhammhassan.reminderobat.di.Module.viewModelModule
import com.muhammhassan.reminderobat.domain.di.Module.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(
                viewModelModule,
                useCaseModule,
                repositoryModule,
                databaseModule,
                datasourceModule,
            ))
        }
    }
}