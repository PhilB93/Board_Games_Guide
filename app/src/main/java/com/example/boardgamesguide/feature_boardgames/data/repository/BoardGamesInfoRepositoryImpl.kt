package com.example.boardgamesguide.feature_boardgames.data.repository

import android.content.Context
import com.example.boardgamesguide.feature_boardgames.domain.model.GameItems
import com.example.boardgamesguide.feature_boardgames.data.remote.ApiService
import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val ERROR_MESSAGE = "smthng wrong"

class BoardGamesInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    val context: Context

) : BoardGamesInfoRepository {

    override fun searchBoardGames(name: String): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {
            delay(1000)
            emit(
                NetworkResult.DataState(
                    apiService.searchBoardGames(
                        name = name
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(exception = e, message = ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

}