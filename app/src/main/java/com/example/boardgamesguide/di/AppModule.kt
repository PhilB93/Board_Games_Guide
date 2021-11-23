package com.example.boardgamesguide.di


import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.boardgamesguide.R
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.domain.repository.BoardGamesInfoRepositoryImpl
import com.example.boardgamesguide.domain.use_case.GetBoardGameByIdUseCase
import com.example.boardgamesguide.domain.use_case.TopGamesUseCase
import com.example.boardgamesguide.domain.use_case.RandomGamesUseCase
import com.example.boardgamesguide.network.ApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.boardgameatlas.com/"
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_search)
            .error(R.drawable.ic_search)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideGetWordInfoUseCase(repository: BoardGamesInfoRepository): TopGamesUseCase =
        TopGamesUseCase(repository)


    @Singleton
    @Provides
    fun provideSearchInfoUseCase(repository: BoardGamesInfoRepository): RandomGamesUseCase =
        RandomGamesUseCase(repository)


    @Singleton
    @Provides
    fun provideGetBoardGameByIdUseCase(repository: BoardGamesInfoRepository):
            GetBoardGameByIdUseCase = GetBoardGameByIdUseCase(repository)

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
