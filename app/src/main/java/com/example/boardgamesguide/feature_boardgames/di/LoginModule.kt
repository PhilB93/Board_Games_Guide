package com.example.boardgamesguide.feature_boardgames.di

import com.example.boardgamesguide.feature_boardgames.data.login.FireBaseSource
import com.example.boardgamesguide.feature_boardgames.data.repository.LoginRepositoryImpl
import com.example.boardgamesguide.feature_boardgames.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LoginModule {
        @Provides
        @Singleton
        fun provideFireBaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
        @Provides
        @Singleton
        fun provideFirestore()= FirebaseFirestore.getInstance()

        @Provides
        @Singleton
        fun provideRepository(
                fireBaseSource: FireBaseSource
        ):LoginRepository= LoginRepositoryImpl(fireBaseSource)
}