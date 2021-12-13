package com.example.boardgamesguide.feature_boardgames.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.boardgamesguide.R
import com.example.boardgamesguide.feature_boardgames.data.local.BoardGamesDatabase
import com.example.boardgamesguide.feature_boardgames.data.remote.ApiService
import com.example.boardgamesguide.feature_boardgames.data.repository.BoardGamesInfoRepositoryImpl
import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.feature_boardgames.domain.use_case.SearchBoardGamesUseCase
import com.example.boardgamesguide.feature_boardgames.domain.use_case.TopBoardGamesUseCase
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
            .placeholder(R.drawable.noimage)
            .error(R.drawable.noimage)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideSearchBoardGamesCase(repository: BoardGamesInfoRepository):
            SearchBoardGamesUseCase = SearchBoardGamesUseCase(repository)

    @Singleton
    @Provides
    fun provideTopBoardGamesCase(repository: BoardGamesInfoRepository):
            TopBoardGamesUseCase = TopBoardGamesUseCase(repository)
    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: ApiService,
        db: BoardGamesDatabase,
    ): BoardGamesInfoRepository {
        return BoardGamesInfoRepositoryImpl(api,db.dao)
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

    @Provides
    @Singleton
    fun provideBoardGamesDatabase(app: Application): BoardGamesDatabase {
        return Room.databaseBuilder(
            app, BoardGamesDatabase::class.java, "games_db"
        ).build()
    }



}
