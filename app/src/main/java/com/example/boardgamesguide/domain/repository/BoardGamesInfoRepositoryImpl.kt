package com.example.boardgamesguide.domain.repository

import com.example.boardgamesguide.domain.model.Game
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.network.ApiService
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BoardGamesInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BoardGamesInfoRepository {
    override fun getBoardGamesInfo(): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {
            delay(1000)
            emit(NetworkResult.DataState(apiService.getCatItems()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(e))
        }
    }.flowOn(Dispatchers.IO)


}