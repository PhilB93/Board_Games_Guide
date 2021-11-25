package com.example.boardgamesguide.di

import android.content.Context
import com.example.boardgamesguide.prefsstore.PrefsStore
import com.example.boardgamesguide.prefsstore.PrefsStoreImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class StoreModule {

    @Singleton
    @Provides
    fun providePrefsStore(@ApplicationContext context: Context):
            PrefsStore = PrefsStoreImpl(context)
}