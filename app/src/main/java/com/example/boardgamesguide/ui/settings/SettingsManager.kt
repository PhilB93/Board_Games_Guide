package com.example.boardgamesguide.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

enum class UIMode {
  LIGHT, DARK
}

class SettingsManager @Inject constructor(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
    private val dataStore: DataStore<Preferences> = context.dataStore

    val uiModeFlow: Flow<UIMode> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())

            } else {
                throw  it
            }
        }
        .map {
            when (it[IS_DARK_MODE] ?: false) {
                true -> UIMode.DARK
                false -> UIMode.LIGHT
            }
        }

    suspend fun setUIMode(uiMode: UIMode) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when (uiMode) {
                UIMode.LIGHT -> false
                UIMode.DARK -> true
            }
        }
    }


    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}