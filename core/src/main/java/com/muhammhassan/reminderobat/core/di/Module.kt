package com.muhammhassan.reminderobat.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.muhammhassan.reminderobat.core.BuildConfig
import com.muhammhassan.reminderobat.core.api.ApiService
import com.muhammhassan.reminderobat.core.api.HeaderInterceptor
import com.muhammhassan.reminderobat.core.database.DrugsReminderDatabase
import com.muhammhassan.reminderobat.core.datasource.LocalDatasource
import com.muhammhassan.reminderobat.core.datasource.LocalDatasourceImpl
import com.muhammhassan.reminderobat.core.datasource.RemoteDatasource
import com.muhammhassan.reminderobat.core.datasource.RemoteDatasourceImpl
import com.muhammhassan.reminderobat.core.datastore.DataStorePreferences
import com.muhammhassan.reminderobat.core.repository.ConsultationRepository
import com.muhammhassan.reminderobat.core.repository.ConsultationRepositoryImpl
import com.muhammhassan.reminderobat.core.repository.DrugRepository
import com.muhammhassan.reminderobat.core.repository.DrugRepositoryImpl
import com.muhammhassan.reminderobat.core.repository.EducationRepository
import com.muhammhassan.reminderobat.core.repository.EducationRepositoryImpl
import com.muhammhassan.reminderobat.core.repository.HistoryRepository
import com.muhammhassan.reminderobat.core.repository.HistoryRepositoryImpl
import com.muhammhassan.reminderobat.core.repository.ScheduleRepository
import com.muhammhassan.reminderobat.core.repository.ScheduleRepositoryImpl
import com.muhammhassan.reminderobat.core.repository.UserRepository
import com.muhammhassan.reminderobat.core.repository.UserRepositoryImpl
import com.muhammhassan.reminderobat.core.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Module {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("patuhi_com")

    val retrofitModule = module {
        fun apiInstance(dataStore: DataStorePreferences): Retrofit {
            val client = OkHttpClient.Builder().addInterceptor(HeaderInterceptor(dataStore))
            if (BuildConfig.DEBUG) {
                client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client.build()).build()
        }

        single { apiInstance(get()).create(ApiService::class.java) }
    }

    val datastoreModule = module {
        single { DataStorePreferences(androidContext().datastore) }
    }
    val databaseModule = module {
        fun dbInstance(context: Context): DrugsReminderDatabase {
            return Room.databaseBuilder(context, DrugsReminderDatabase::class.java, Constant.dbName)
                .fallbackToDestructiveMigration().build()
        }

        single { dbInstance(androidContext()) }
    }

    val datasourceModule = module {
        single<LocalDatasource> { LocalDatasourceImpl(get(), get()) }
        single<RemoteDatasource> { RemoteDatasourceImpl(get()) }
    }

    val repositoryModule = module {
        single<DrugRepository> { DrugRepositoryImpl(get()) }
        single<HistoryRepository> { HistoryRepositoryImpl(get()) }
        single<ScheduleRepository> { ScheduleRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
        single<EducationRepository> { EducationRepositoryImpl(get()) }
        single<ConsultationRepository> { ConsultationRepositoryImpl(get()) }
    }
}