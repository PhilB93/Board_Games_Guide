package com.example.boardgamesguide.domain.repository



import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface BoardGamesInfoRepository {

    fun topBoardGames(
    ): Flow<NetworkResult<GameItems>>

    fun randomBoardGameInfo(
    ): Flow<NetworkResult<GameItems>>

    fun getBoardGameById(
        id:String
    ): Flow<NetworkResult<GameItems>>

    fun searchBoardGames(
        name:String
    ): Flow<NetworkResult<GameItems>>

}