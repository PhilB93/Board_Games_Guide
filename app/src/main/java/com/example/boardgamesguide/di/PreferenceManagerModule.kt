package com.example.boardgamesguide.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.boardgamesguide.ui.settings.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceManagerModule {

    @Singleton
    @Provides
    fun settingsManagerProvide(@ApplicationContext context: Context): SettingsManager =
        SettingsManager(context)

}