package com.example.boardgamesguide.feature_boardgames.domain.repository



import com.example.boardgamesguide.feature_boardgames.data.model.GameDto



interface BoardGamesInfoRepository {
    suspend fun searchBoardGames(
        name:String
    ): List<GameDto>

}