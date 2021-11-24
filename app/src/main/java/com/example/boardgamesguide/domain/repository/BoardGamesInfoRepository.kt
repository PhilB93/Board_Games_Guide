package com.example.boardgamesguide.domain.repository


import com.example.boardgamesguide.domain.model.Game
import com.example.boardgamesguide.domain.model.GameItems
import com.example.boardgamesguide.network.ApiService
import com.example.boardgamesguide.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface BoardGamesInfoRepository {

    fun topBoardGames(
    ): Flow<NetworkResult<GameItems>>

    fun randomBoardGameInfo(
        gt_min_players: Int,
        lt_max_players: Int,
        gt_min_playtime:Int,
        lt_max_playtime:Int
    ): Flow<NetworkResult<GameItems>>

    fun getBoardGameById(
        id:String
    ): Flow<NetworkResult<GameItems>>
}