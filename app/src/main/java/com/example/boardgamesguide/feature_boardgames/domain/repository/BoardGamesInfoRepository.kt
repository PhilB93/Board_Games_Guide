package com.example.boardgamesguide.feature_boardgames.domain.repository

import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface BoardGamesInfoRepository {
     fun searchBoardGames(
        name: String
    ): Flow<Resource<List<Game>>>

    fun getBoardGames(
    ): Flow<Resource<List<Game>>>
}