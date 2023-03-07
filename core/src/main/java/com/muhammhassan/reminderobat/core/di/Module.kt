package com.muhammhassan.reminderobat.core.di

import android.content.Context
import androidx.room.Room
import com.muhammhassan.reminderobat.core.database.DrugsReminderDatabase
import com.muhammhassan.reminderobat.core.datasource.LocalDatasource
import com.muhammhassan.reminderobat.core.datasource.LocalDatasourceImpl
import com.muhammhassan.reminderobat.core.repository.*
import com.muhammhassan.reminderobat.core.utils.Constant
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object Module {
    val databaseModule = module {
        fun dbInstance(context: Context): DrugsReminderDatabase{
            return Room.databaseBuilder(context, DrugsReminderDatabase::class.java, Constant.dbName)
                .fallbackToDestructiveMigration()
                .build()
        }

        single { dbInstance(androidContext()) }
    }

    val datasourceModule = module {
        single<LocalDatasource> { LocalDatasourceImpl(get()) }
    }

    val repositoryModule = module {
        single<DrugRepository> {DrugRepositoryImpl(get())}
        single<HistoryRepository> { HistoryRepositoryImpl(get()) }
        single<ScheduleRepository> { ScheduleRepositoryImpl(get()) }
    }
}