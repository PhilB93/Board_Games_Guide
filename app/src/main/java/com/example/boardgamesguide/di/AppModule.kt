package com.example.boardgamesguide.di


import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepositoryImpl
import com.example.boardgamesguide.domain.use_case.GetBoardGamesInfo
import com.example.boardgamesguide.network.ApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private  const val BASE_URL = "https://api.boardgameatlas.com/"
@Module
@InstallIn(SingletonComponent::class)
class AppModule {



    @Singleton
    @Provides
    fun provideGetWordInfoUseCase(repository: BoardGamesInfoRepository): GetBoardGamesInfo {
        return GetBoardGamesInfo(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: ApiService
    ): BoardGamesInfoRepository {
        return BoardGamesInfoRepositoryImpl(api)
    }


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}
