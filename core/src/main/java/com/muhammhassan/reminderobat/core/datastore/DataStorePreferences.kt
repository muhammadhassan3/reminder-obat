package com.muhammhassan.reminderobat.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences (private val datastore: DataStore<Preferences>) {
    fun getToken(): Flow<String?> {
        return datastore.data.map { preferences ->
            preferences[AUTH_TOKEN]
        }
    }

    suspend fun setToken(token: String) {
        datastore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }

    companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }
}