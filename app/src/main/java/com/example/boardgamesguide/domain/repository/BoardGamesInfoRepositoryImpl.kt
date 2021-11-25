package com.example.boardgamesguide.domain.repository

import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.network.ApiService
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val LIMIT = 10
private const val ORDER = "rank"

class BoardGamesInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BoardGamesInfoRepository {
    override fun topBoardGames(): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {
            emit(
                NetworkResult.DataState(
                    apiService.topBoardGames(
                        limit = LIMIT,
                        order_by = ORDER
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun randomBoardGameInfo(
    ): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {

            emit(
                NetworkResult.DataState(
                    apiService.randomBoardGames(
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getBoardGameById(id: String): Flow<NetworkResult<GameItems>> = flow {
        emit(NetworkResult.LoadingState)
        try {
            emit(NetworkResult.DataState(apiService.getGameById(id = id)))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.ErrorState(e))


        }
    }
}