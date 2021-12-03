package com.example.boardgamesguide.feature_boardgames.data.repository

import android.content.Context
import com.example.boardgamesguide.feature_boardgames.data.model.toGame
import com.example.boardgamesguide.feature_boardgames.data.remote.ApiService
import com.example.boardgamesguide.feature_boardgames.domain.model.Game
import com.example.boardgamesguide.feature_boardgames.domain.repository.BoardGamesInfoRepository
import javax.inject.Inject

class BoardGamesInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    val context: Context
) : BoardGamesInfoRepository {

    override suspend fun searchBoardGames(name: String): List<Game> =
        apiService.searchBoardGames(name).games.map { it.toGame() }
}
