package com.example.boardgamesguide.feature_boardgames.domain.repository

import com.example.boardgamesguide.feature_boardgames.domain.model.Game

interface BoardGamesInfoRepository {
    suspend fun searchBoardGames(
        name: String
    ): List<Game>
}